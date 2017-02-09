package com.xutils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.table.DbModel;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import util.Child;
import util.Parent;

/**
 * 数据库DB
 */
@ContentView(R.layout.db_activity)
public class DbActivity extends Activity {

	@ViewInject(R.id.db_test_btn)
	private Button stopBtn;

	@ViewInject(R.id.result_txt)
	private TextView resultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
	}

	@OnClick(R.id.db_test_btn)
	public void testDb(View view) {
		String temp = "";
		Parent parent = new Parent();
		parent.name = "测试" + System.currentTimeMillis();
		parent.setAdmin(true);
		parent.setEmail("2635481328@qq.com");

		try {
			// DbUtils db = DbUtils.create(this, "/sdcard/", "test.db");
			DbUtils db = DbUtils.create(this);
			db.configAllowTransaction(true);
			db.configDebug(true);

			Child child = new Child();
			child.name = "child' name";
			// db.saveBindingId(parent);
			// child.parent = new ForeignLazyLoader<Parent>(Child.class,
			// "parentId", parent.getId());
			// child.parent = parent;

			Parent test = db.findFirst(parent); // 通过parent的属性查找
			// Parent test =
			// db.findFirst(Selector.from(Parent.class).where("id", "in", new
			// int[]{1, 3, 6}));
			// Parent test =
			// db.findFirst(Selector.from(Parent.class).where("id", "between",
			// new String[]{"1", "5"}));
			if (test != null) {
				child.parent = test;
				temp += "first parent:" + test + "\n";
				resultText.setText(temp);
			} else {
				child.parent = parent;
			}

			parent.setTime(new Date());
			parent.setDate(new java.sql.Date(new Date().getTime()));

			db.saveBindingId(child);// 保存对象关联数据库生成的id

			List<Child> children = db.findAll(Selector.from(Child.class));// .where(WhereBuilder.b("name",
																			// "=",
																			// "child' name")));
			temp += "children size:" + children.size() + "\n";
			resultText.setText(temp);
			if (children.size() > 0) {
				temp += "last children:" + children.get(children.size() - 1)
						+ "\n";
				resultText.setText(temp);
			}

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			calendar.add(Calendar.HOUR, 3);

			List<Parent> list = db.findAll(Selector.from(Parent.class)
					.where("id", "<", 54).and("time", ">", calendar.getTime())
					.orderBy("id").limit(10));
			temp += "find parent size:" + list.size() + "\n";
			resultText.setText(temp);
			if (list.size() > 0) {
				temp += "last parent:" + list.get(list.size() - 1) + "\n";
				resultText.setText(temp);
			}

			// parent.name = "hahaha123";
			// db.update(parent);

			Parent entity = db.findById(Parent.class, child.parent.getId());
			temp += "find by id:" + entity.toString() + "\n";
			resultText.setText(temp);

			List<DbModel> dbModels = db.findDbModelAll(Selector
					.from(Parent.class).groupBy("name")
					.select("name", "count(name) as count"));
			temp += "group by result:" + dbModels.get(0).getDataMap() + "\n";
			resultText.setText(temp);

		} catch (DbException e) {
			temp += "error :" + e.getMessage() + "\n";
			resultText.setText(temp);
		}
	}
}
