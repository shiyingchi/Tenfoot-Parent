package com.project.server.user.impl;

import net.sf.json.JSONObject;
import com.project.dao.user.TuserDao;
import com.project.domain.user.TuserDO;
import com.project.server.user.TuserService;
import com.project.utils.Constant;
import com.project.utils.R;
import com.project.utils.WebClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;



@Service
public class TuserServiceImpl implements TuserService {
	@Autowired
	private TuserDao tuserDao;

	/**
	 * 授权登陆
	 * @param code
	 * @return
	 */
	@Override
	public Map<String, Object> weixinLong(String code) {
		if(StringUtils.isEmpty(code)){
			return R.hashMapError("code不能为空");
		}else{
			String openIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Constant.PARAM_MAP.get("appid") + "&secret=" + Constant.PARAM_MAP.get("secret") + "&js_code=" + code + "&grant_type=authorization_code";
			WebClient ticketWeb = new WebClient(openIdUrl, WebClient.HTTPMethod.GET);
			ticketWeb.setTimeout(5000, 10000);
			try {
				String openIdResp = ticketWeb.getTextContent();
				if (openIdResp!=null) {
					JSONObject object = JSONObject.fromObject(openIdResp);

					if (object!=null) {
						if (object.get("openid")!=null) {
							return R.hashMapOk("授权成功",object);
						} else {
							return R.hashMapError("登陆失败！");
						}
					}else{
						return R.hashMapError("授权失败");
					}
				}else{
					return R.hashMapError("openIdResp对象为空");
				}
			} catch (IOException e) {
					return R.hashMapError("json数据装载成对象数据出错");
			}
		}
	}


	@Override
	public Map<String, Object> userInformation(TuserDO tuserDO) {
		if(StringUtils.isEmpty(tuserDO.getOpenId())){
			return R.hashMapError("用户id不能为空");
		}else{
			TuserDO user =  tuserDao.get(tuserDO.getOpenId());
			//查询不到则添加记录   否则修改记录
			if(user == null){
				tuserDO.setCreateTime(new Date());
				tuserDao.save(tuserDO);
			}else{
				tuserDO.setLoginTime(new Date());
				tuserDO.setPhone(user.getPhone());
				tuserDO.setUserId(user.getUserId());
				tuserDao.update(tuserDO);
			}
			return R.hashMapOk("操作成功",tuserDO);
		}
	}

}
