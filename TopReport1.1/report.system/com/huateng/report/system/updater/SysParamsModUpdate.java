package com.huateng.report.system.updater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import resource.bean.report.SysParams;
import resource.bean.report.SysParamsPK;

import com.huateng.common.DateUtil;
import com.huateng.common.err.Module;
import com.huateng.common.err.Rescode;
import com.huateng.commquery.result.MultiUpdateResultBean;
import com.huateng.commquery.result.UpdateResultBean;
import com.huateng.commquery.result.UpdateReturnBean;
import com.huateng.ebank.business.common.GlobalInfo;
import com.huateng.ebank.framework.operation.OPCaller;
import com.huateng.ebank.framework.operation.OperationContext;
import com.huateng.ebank.framework.web.commQuery.BaseUpdate;
import com.huateng.exception.AppException;
import com.huateng.report.system.operation.SysParamsOperation;

/*
 * 系统参数设置的update
 * 
 */
public class SysParamsModUpdate extends BaseUpdate {

	private static final String DATASET_ID = "sysParamsEntry";

	@Override
	public UpdateReturnBean saveOrUpdate(MultiUpdateResultBean multiUpdateResultBean, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			// 返回对象
			UpdateReturnBean updateReturnBean = new UpdateReturnBean();
			// 结果集对象
			UpdateResultBean updateResultBean = multiUpdateResultBean.getUpdateResultBeanByID(DATASET_ID);
			// 更新对象
			SysParams sysParams = new SysParams();
			// Operation参数
			OperationContext context = new OperationContext();
			// 获得最后操作时间
			String lasttime = DateUtil.get14Date();
			// 需更新原纪录集合
			List oldUpdateList = new ArrayList();
			// 需写入审计任务表集合
			List tskList = new ArrayList();

			if (updateResultBean.hasNext()) {
				// 属性拷贝
				Map map = updateResultBean.next();

				context.setAttribute(SysParamsOperation.CMD, SysParamsOperation.CMD_MOD);
				BaseUpdate.mapToObject(sysParams, map);
				// 联合主键,不能拷贝属性,需手动装配下
				String paramgroupId = (String) map.get("paramgroupId");
				String paramId = (String) map.get("paramId");

				sysParams.setId(new SysParamsPK(paramgroupId, paramId));
				// 需要跟新的一些数据

				sysParams.setSt("1");
				sysParams.setLock(true);
				sysParams.setDel(false);
				sysParams.setCrtDt(DateUtil.get8Date());
				sysParams.setLastUpdTms(lasttime);
				sysParams.setLastUpdOper(new GlobalInfo().getTlrno());

				unnvlSysParams(sysParams);

				context.setAttribute(SysParamsOperation.IN_PARAM, sysParams);
				// call方式开启operation事务
				OPCaller.call(SysParamsOperation.ID, context);
				return updateReturnBean;
			}
		} catch (AppException appe) {
			throw appe;
		} catch (Exception e) {
			throw new AppException(Module.SYSTEM_MODULE, Rescode.DEFAULT_RESCODE, e.getMessage(), e);
		}
		return null;
	}

	/*
	 * 将属性为空字符串的转换成null
	 * 
	 */
	private void unnvlSysParams(SysParams sysParams) {
		if (StringUtils.isBlank(sysParams.getParamName())) {
			sysParams.setParamName(null);
		}
		if (StringUtils.isBlank(sysParams.getParamVal())) {
			sysParams.setParamVal(null);
		}
		if (StringUtils.isBlank(sysParams.getMemo())) {
			sysParams.setMemo(null);
		}
	}

}
