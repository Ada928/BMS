package com.huateng.report.send.bean;

import java.io.Serializable;

public class ReportFeedBackErrField implements Serializable {
	private String errField;
	private String errFieldCn;
	private String errDesc;

	public String getErrField() {
		return errField;
	}

	public void setErrField(String errField) {
		this.errField = errField;
	}

	public String getErrFieldCn() {
		return errFieldCn;
	}

	public void setErrFieldCn(String errFieldCn) {
		this.errFieldCn = errFieldCn;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

}
