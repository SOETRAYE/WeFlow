package com.cmmobi.railwifi.alipay;import org.json.JSONException;import org.json.JSONObject;import android.util.Log;public class Result {	String resultStatus;	String result;	String memo;	private PayInfo payInfoRet = null;	public Result(String rawResult) {		try {			String[] resultParams = rawResult.split(";");			for (String resultParam : resultParams) {				if (resultParam.startsWith("resultStatus")) {					resultStatus = gatValue(resultParam, "resultStatus");				}				if (resultParam.startsWith("result")) {					result = gatValue(resultParam, "result");				}				if (resultParam.startsWith("memo")) {					memo = gatValue(resultParam, "memo");				}			}		} catch (Exception e) {			e.printStackTrace();		}	}	@Override	public String toString() {		return "resultStatus={" + resultStatus + "};memo={" + memo				+ "};result={" + result + "}";	}	private String gatValue(String content, String key) {		String prefix = key + "={";		return content.substring(content.indexOf(prefix) + prefix.length(),				content.lastIndexOf("}"));	}		public PayInfo getPayInfo() {		return payInfoRet;	}		public String getResultNo() {		return resultStatus;	}		public boolean isTransactionSecc() {		return resultStatus.equals("9000");	}		public boolean isUserCancle() {		return resultStatus.equals("6001");	}		public String getTrandeNo() {		if (payInfoRet != null) {			return payInfoRet.out_trade_no;		}		return null;	}			private void parserPayInfo(String result) {		try {			JSONObject json = string2JSON(result, "&");						PayInfo payInfo = new PayInfo();			payInfo.setService(getJSONItemString(json, "service"));			payInfo.setPartner(getJSONItemString(json, "partner"));			payInfo.set_input_charset(getJSONItemString(json, "_input_charset"));			payInfo.setSign_type(getJSONItemString(json, "sign_type"));			payInfo.setSign(getJSONItemString(json, "sign"));			payInfo.setNotify_url(getJSONItemString(json, "notify_url"));			payInfo.setApp_id(getJSONItemString(json, "app_id"));			payInfo.setAppenv(getJSONItemString(json, "appenv"));			payInfo.setOut_trade_no(getJSONItemString(json, "out_trade_no"));			payInfo.setSubject(getJSONItemString(json, "subject"));			payInfo.setPayment_type(getJSONItemString(json, "payment_type"));			payInfo.setSeller_id(getJSONItemString(json, "seller_id"));			payInfo.setTotal_fee(getJSONItemString(json, "total_fee"));			payInfo.setBody(getJSONItemString(json, "body"));			payInfo.setIt_b_pay(getJSONItemString(json, "it_b_pay"));			payInfo.setShow_url(getJSONItemString(json, "show_url"));			payInfo.setExtern_token(getJSONItemString(json, "extern_token"));						this.payInfoRet = payInfo;			Log.d("Result","payInfo = " + payInfo);		} catch (Exception e) {			e.printStackTrace();			Log.i("Result", "Exception =" + e);		}	}		public  JSONObject string2JSON(String src, String split) {		JSONObject json = new JSONObject();		try {			String[] arr = src.split(split);			for (int i = 0; i < arr.length; i++) {				String[] arrKey = arr[i].split("=");				json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));			}		} catch (Exception e) {			e.printStackTrace();		}		return json;	}		private String getJSONItemString(JSONObject json,String item) {		String value = "";		try {			value = json.getString(item);			value = value.replace("\"", "");		} catch (JSONException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}				return value;	}}