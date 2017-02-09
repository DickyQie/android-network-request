package util;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

@Table(name = "child")
// 建议加上注解， 混淆后表名不受影响
public class Child extends EntityBase {

	@Column(column = "name")
	public String name;

	@Column(column = "email")
	private String email;

	@Foreign(column = "parentId", foreign = "id")
	public Parent parent;

	// Transient使这个列被忽略，不存入数据库
	@Transient
	public String willIgnore;

	public static String staticFieldWillIgnore; // 静态字段也不会存入数据库

	@Column(column = "text")
	private String text;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Child{" + "id=" + getId() + ", name='" + name + '\''
				+ ", email='" + email + '\'' + ", parent=" + parent
				+ ", willIgnore='" + willIgnore + '\'' + ", text='" + text
				+ '\'' + '}';
	}
}
