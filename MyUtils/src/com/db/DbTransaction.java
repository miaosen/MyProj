package com.db;

import java.util.ArrayList;
import java.util.List;

import com.comment.Logger;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DbTransaction {

	public SQLiteDatabase db;

	public DbUtils dbUtils;

	public List<TablePty> tablePtys;

	public DbTransaction(DbUtils dbUtils) {
		this.dbUtils = dbUtils;
		this.db = dbUtils.getDataBase();
	}

	/**
	 * 添加数据事务 
	 * @return
	 */
	public boolean insert() {
		boolean success = true;
		if (tablePtys != null) {
			try {
				db.beginTransaction();
				for (int i = 0; i < tablePtys.size(); i++) {
					TablePty tablePty = tablePtys.get(i);
					if (tablePty.getValue() != null) {
						long insertSucces = dbUtils.addRow(tablePty.getName(),
								tablePty.getValue());
						if (insertSucces == -1) {
							success = false;
							i = tablePtys.size();
						}
					} else if (tablePty.getListVc() != null) {
						List<ContentValues> listVc = tablePty.getListVc();
						for (int j = 0; j < listVc.size(); j++) {
							long insertSucces = dbUtils.addRow(
									tablePty.getName(), listVc.get(j));
							if (insertSucces == -1) {
								j = listVc.size();
								success = false;
							}
						}
					} else {
						Logger.info("数据格式不对！");
						success=false;
					}
				}
				if (success) {
					db.setTransactionSuccessful();
				} else {
					Logger.info("本次所有数据保存不成功！事务回滚");
				}
			} catch (Exception e) {
				e.printStackTrace();
				success = false;
			} finally {
				db.endTransaction();
			}
		} else {
			success = false;
			Logger.info("无表格数据！");
		}
		return success;
	}

	/**
	 * 一个表对应一条数据
	 * 
	 * @param name
	 * @param values
	 */
	public void addTableRow(String name, ContentValues values) {
		if (tablePtys == null) {
			tablePtys = new ArrayList<TablePty>();
		}
		tablePtys.add(new TablePty(name, values));
	}

	/**
	 * 一个表对应多条数据
	 * 
	 * @param name
	 * @param values
	 */
	public void addTableRows(String name, List<ContentValues> values) {
		if (tablePtys == null) {
			tablePtys = new ArrayList<TablePty>();
		}
		tablePtys.add(new TablePty(name, values));
	}

}
