package xatu.school.bean;


/**
 * 功能：
 */
public class RadioCheck
{
	private String content; // 问题
	private String answer; // 答案
	public static String[] answers = {"A","B","C","D","E"}; // 选项编号和答案编号
	public RadioCheck(String content) {
		this.content = content;
	}
	public String getAnswer()
	{
		return answer;
	}

	public void setAnswer(String answer)
	{
		this.answer = answer;
	}


	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

}
