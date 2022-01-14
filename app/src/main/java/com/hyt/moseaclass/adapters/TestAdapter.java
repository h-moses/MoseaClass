package com.hyt.moseaclass.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hyt.moseaclass.data.entity.TestQuestion;
import com.hyt.moseaclass.data.entity.TestQuestionSet;
import com.hyt.moseaclass.databinding.ItemTestBinding;
import com.hyt.moseaclass.state.UserContext;
import com.hyt.moseaclass.ui.course.AnswerActivity;
import com.hyt.moseaclass.utils.OkHttpUtils;
import com.hyt.moseaclass.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.FormBody;

/*
 * 测验卡片适配器
 * */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {

    //    总分模板
    private static final String SCORE_TEMPLATE = "总分%d";
    //    标题模板
    private static final String TITLE_TEMPLATE = "第%s章测验";
    //    时间模板
    private static final String DEADLINE_TEMPLATE = "提交截止 2022-02-27 23:00";
    private static final String TAG = TestAdapter.class.getSimpleName();


    private final Context mContext;
    //    所有章节的总分数组
    private final List<Integer> scoreList = new ArrayList<>();
    //    所有章节的信息和题目实体数组
    private final List<TestQuestionSet> questions = new ArrayList<>();
    //    章节匹配字典
    private final Map<Integer, String> numberMap = new HashMap<>();

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
        try {
            initData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
     * 异步请求获取数据，并解析数据
     * */
    private void initData() throws JSONException {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("id", String.valueOf(SharedPreferenceUtils.getInteger(mContext, SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_CID, Integer.MIN_VALUE)));
        JSONArray array = OkHttpUtils.post("http://101.133.173.40:8090/edusys/course/getCourseTest", builder.build());
        for (int i = 0; i < array.length(); i++) {
            TestQuestionSet questionSet = null;
            int totalScore = 0;
            JSONObject jsonObject = array.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String title = jsonObject.getString("title");
            boolean objectNull = jsonObject.isNull("testList");
            List<TestQuestion> questionList = null;
            if (objectNull || jsonObject.getJSONArray("testList").length() == 0) { // 若该章节下没有测试题目，直接略过
                continue;
            } else {
                questionList = new ArrayList<>();
                JSONArray single = jsonObject.getJSONArray("testList").getJSONObject(0).getJSONArray("singleQuestions");
                JSONArray judge = jsonObject.getJSONArray("testList").getJSONObject(0).getJSONArray("judgeQuestions");
//                循环取出单选题所需的信息
                for (int j = 0; j < single.length(); j++) {
                    TestQuestion testQuestion;
                    JSONObject object = single.getJSONObject(j);
                    int id1 = object.getInt("id");
                    int score = object.getInt("score");
                    totalScore += score;
                    String question_type = object.getString("question_type");
                    String question = object.getJSONObject("questionSingle").getString("question");
                    String answer = object.getJSONObject("questionSingle").getString("answer");
                    List<String> options = new ArrayList<>();
//                    添加单选项
                    options.add(object.getJSONObject("questionSingle").getString("optionA"));
                    options.add(object.getJSONObject("questionSingle").getString("optionB"));
                    options.add(object.getJSONObject("questionSingle").getString("optionC"));
                    options.add(object.getJSONObject("questionSingle").getString("optionD"));
                    testQuestion = new TestQuestion(id1, score, question_type, question, answer, options);
                    questionList.add(testQuestion);
                }
//                取出判断题所需的信息
                for (int j = 0; j < judge.length(); j++) {
                    TestQuestion testQuestion;
                    JSONObject object = judge.getJSONObject(j);
                    int id1 = object.getInt("id");
                    int score = object.getInt("score");
                    totalScore += score;
                    String question_type = object.getString("question_type");
                    String question = object.getJSONObject("questionJudge").getString("question");
                    String answer = object.getJSONObject("questionJudge").getString("answer");
                    List<String> options = new ArrayList<>();
//                    添加判断选项
                    options.add("正确");
                    options.add("错误");
                    testQuestion = new TestQuestion(id1, score, question_type, question, answer, options);
                    questionList.add(testQuestion);
                }
                questionSet = new TestQuestionSet(id, title, questionList);
            }
            scoreList.add(totalScore);
            questions.add(questionSet);
        }

        numberMap.put(1, "一");
        numberMap.put(2, "二");
        numberMap.put(3, "三");
        numberMap.put(4, "四");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        com.hyt.moseaclass.databinding.ItemTestBinding binding = ItemTestBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        根据模板和数据显示对应标题信息
        holder.getBinding().testTitle.setText(String.format(Locale.CHINA, TITLE_TEMPLATE, numberMap.get(questions.get(position).getId())));
        holder.getBinding().testDeadline.setText(DEADLINE_TEMPLATE);
//        根据模板和数据显示总分信息
        holder.getBinding().testScore.setText(String.format(Locale.CHINA, SCORE_TEMPLATE, scoreList.get(position)));
//        按钮添加点击监听器
        holder.getBinding().btnAccessTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserContext.getInstance().getIsLogin(view.getContext())) { // 若用户已登录
                    if (SharedPreferenceUtils.getBoolean(view.getContext(), SharedPreferenceUtils.COURSE_FILE, UserContext.KEY_JOIN, false)) { // 若用户已参与该课程
//                        显示确认对话框，是否进入答题界面
                        new AlertDialog.Builder(view.getContext()).setMessage("确认要进入答题？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(mContext, AnswerActivity.class);
                                intent.putExtra("data", (Serializable) questions.get(position).getQuestionList());
                                mContext.startActivity(intent);
                            }
                        }).setNegativeButton("放弃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create().show();
                    } else { // 若用户没有参与课程
                        Toast.makeText(view.getContext(), "用户未加入该课程，无法进入测验！", Toast.LENGTH_SHORT).show();
                    }
                } else { // 若用户没有登录
                    Toast.makeText(view.getContext(), "用户未登录，无法进入测验！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemTestBinding binding;

        public ViewHolder(@NonNull ItemTestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ItemTestBinding getBinding() {
            return binding;
        }
    }
}
