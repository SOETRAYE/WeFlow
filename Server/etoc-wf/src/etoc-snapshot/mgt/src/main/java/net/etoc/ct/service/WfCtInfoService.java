/**
 * 创建时间
 * 2015年3月22日-下午3:05:05
 * 
 * 
 */
package net.etoc.ct.service;

import java.lang.reflect.InvocationTargetException;

import net.etoc.ct.entity.CtInfoRequest;
import net.etoc.ct.entity.CtInfoResponse;
import net.etoc.ct.entity.WfCtInfo;
import net.etoc.wf.ctapp.base.RequestBase;
import net.etoc.wf.ctapp.base.ResponseBase;
import net.etoc.wf.ctapp.user.entity.CrmGameParamRequest;
import net.etoc.wf.ctapp.user.entity.CrmGameParamResponse;
import net.etoc.wf.ctapp.user.entity.FeedBackRequest;
import net.etoc.wf.ctapp.user.entity.UserSignResponse;

import org.springframework.web.client.RestClientException;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 
 * @author yuxuan
 *
 *         2015年3月22日 下午3:05:05
 * 
 * @version 1.0.0
 * 
 */
public interface WfCtInfoService {
	public void saveOrupdate(WfCtInfo bo);

	public void delete(int id);

	public CtInfoResponse findCtInfo(CtInfoRequest rq)
			throws IllegalAccessException, InvocationTargetException;

	public ResponseBase feedBack(String methodSuffix, FeedBackRequest fr)
			throws RestClientException, JsonProcessingException;

	public UserSignResponse sign(String methodSuffix, RequestBase fr)
			throws RestClientException, JsonProcessingException;

	public CrmGameParamResponse queryGameParam(String methodSuffix,
			CrmGameParamRequest fr) throws RestClientException,
			JsonProcessingException;

}
