package com.hyt.moseaclass.components.courseModule;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hyt.moseaclass.R;
import com.hyt.moseaclass.data.entity.CourseChapter;
import com.hyt.moseaclass.data.entity.CourseSection;
import com.hyt.moseaclass.databinding.FragmentCoursewareBinding;
import com.hyt.moseaclass.ui.course.VideoPlayerActivity;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.FormBody;

public class CoursewareFragment extends Fragment {

    private FragmentCoursewareBinding binding;

    private final Context mContext;
    private final List<CourseChapter> chapterList = new ArrayList<>();
    private final List<List<CourseSection>> sectionList = new ArrayList<>();

    public CoursewareFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCoursewareBinding.inflate(inflater, container, false);
        binding.coursewareExpandList.setAdapter(new CourseWareAdapter(getContext(), chapterList, sectionList));
        return binding.getRoot();
    }

    private void initData() throws JSONException {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(SharedPreferenceUtils.getInteger(requireContext(),SharedPreferenceUtils.COURSE_FILE, "cid", Integer.MIN_VALUE)));
        JSONArray jsonArray = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/getCourseCatalogue?", builder.build());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            JSONArray videoList = jsonObject.getJSONArray("videoList");
            CourseChapter courseChapter = new CourseChapter(id, title);
            List<CourseSection> list = new ArrayList<>();
            for (int j = 0; j < videoList.length(); j++) {
                JSONObject video = videoList.getJSONObject(j);
                int vId = video.getInt("id");
                int order_id = video.getInt("order_id");
                String vName = video.getString("title");
                String video_url = video.getString("video_url");
                CourseSection courseSection = new CourseSection(vId, order_id, vName, video_url);
                list.add(courseSection);
            }
            courseChapter.setSectionList(list);
            chapterList.add(courseChapter);
            sectionList.add(list);
        }
    }


    static class CourseWareAdapter implements ExpandableListAdapter {

        private static final String ChapterNameTemplate = "第 %d 章 %s";
        private static final String SectionNameTemplate = "%d - %d %s";
        private static final String TAG = CourseWareAdapter.class.getSimpleName();
        private final Context context;
        private final List<CourseChapter> chapterList;
        private final List<List<CourseSection>> sectionList;

        public CourseWareAdapter(Context context, List<CourseChapter> chapterList, List<List<CourseSection>> sectionList) {
            this.context = context;
            this.chapterList = chapterList;
            this.sectionList = sectionList;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {

        }

        @Override
        public int getGroupCount() {
            return chapterList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return sectionList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return chapterList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return sectionList.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return chapterList.get(groupPosition).getcId();
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return sectionList.get(groupPosition).get(childPosition).getsId();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ChapterViewHolder chapterViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_chapter, parent, false);
                chapterViewHolder = new ChapterViewHolder();
                chapterViewHolder.tv_chapter_name = convertView.findViewById(R.id.tv_chapter_name);
                convertView.setTag(chapterViewHolder);
            } else {
                chapterViewHolder = (ChapterViewHolder) convertView.getTag();
            }
            chapterViewHolder.tv_chapter_name.setText(String.format(Locale.CHINA, ChapterNameTemplate, chapterList.get(groupPosition).getcId(), chapterList.get(groupPosition).getcName()));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            SectionViewHolder sectionViewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_section, parent, false);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(parent.getContext(), VideoPlayerActivity.class);
                        intent.putExtra("video_url",sectionList.get(groupPosition).get(childPosition).getsUrl());
                        context.startActivity(intent);
                    }
                });
                sectionViewHolder = new SectionViewHolder();
                sectionViewHolder.tv_section_name = convertView.findViewById(R.id.tv_section_name);
                convertView.setTag(sectionViewHolder);
            } else {
                sectionViewHolder = (SectionViewHolder) convertView.getTag();
            }

            sectionViewHolder.tv_section_name.setText(String.format(Locale.CHINA, SectionNameTemplate, chapterList.get(groupPosition).getcId(), sectionList.get(groupPosition).get(childPosition).getsOrderId(), sectionList.get(groupPosition).get(childPosition).getsName()));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return true;
        }

        @Override
        public boolean isEmpty() {
            return chapterList.isEmpty() && sectionList.isEmpty();
        }

        @Override
        public void onGroupExpanded(int groupPosition) {

        }

        @Override
        public void onGroupCollapsed(int groupPosition) {

        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            return 0;
        }

        static class ChapterViewHolder {
            private TextView tv_chapter_name;
        }

        static class SectionViewHolder {
            private TextView tv_section_name;
        }
    }
}
