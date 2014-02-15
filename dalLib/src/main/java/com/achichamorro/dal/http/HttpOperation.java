package com.aichamorro.dal.http;

public class HttpOperation {
	static public final int GET = 100;
	static public final int POST = 101;
	static public final int PUT = 102;
	static public final int DELETE = 103;

	private String _url;
	private int _type;

	public HttpOperation(String url) {
		_url = url;
		_type = HttpOperation.GET;
	}

	public void setMethod(int type) {
		_type = type;
	}
}
