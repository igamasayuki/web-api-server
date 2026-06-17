package com.example.practice.common;

/**
 * ステータスを表すenum.
 * 
 * @author igamasayuki
 *
 */
public enum StatusEnum {

	NOT_PAYMENT(0, "入金前", 1), DEPOSITED(1, "入金済", 2), DELIVERED(2, "配送済", 3), COMPLETE(3, "完了", 0);

	// 定数に含めるフィールド、コンストラクタ、メソッドを定義
	/** 現在のステータス */
	private final int statusValue;
	/** 状態名 */
	private final String statusName;
	/** 次のステータス */
	private final int nextStatusValue;

	/**
	 * コンストラクタ。
	 */
	private StatusEnum(int statusValue, String statusName, int nextStatusValue) {
		this.statusValue = statusValue;
		this.statusName = statusName;
		this.nextStatusValue = nextStatusValue;
	}

	/**
	 * statusからenumを取得
	 * 
	 * @param status 番号
	 * @return 渡された番号を含むenum
	 */
	public static StatusEnum of(int status) {
		for (StatusEnum genderEnum : StatusEnum.values()) {
			if (genderEnum.statusValue == status) {
				return genderEnum;
			}
		}
		throw new IndexOutOfBoundsException("The value of intelligibility doesn't exist.");
	}

	public int getStatusValue() {
		return statusValue;
	}

	public String getStatusName() {
		return statusName;
	}

	public int getNextStatusValue() {
		return nextStatusValue;
	}

}
