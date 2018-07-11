package com.project.common.shiro;

import com.project.dao.system.UserDao;
import com.project.domain.system.UserDO;
import com.project.server.system.MenuService;
import com.project.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

public class UserRealm extends AuthorizingRealm {
	@Autowired
	UserDao userMapper;
	@Autowired
	MenuService menuService;
//	@Autowired
//	StringRedisTemplate stringRedisTemplate;





	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Set<String> perms = menuService.listPerms(ShiroUtils.getUser());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		Map<String, Object> map = new HashMap<>(16);
		map.put("username", username);
		String password = new String((char[]) token.getCredentials());

//		ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
//		opsForValue.increment(Constant.SHIRO_LOGIN_COUNT+username, 1);
		//计数大于5时，设置用户被锁定一小时
//		if(Integer.parseInt(opsForValue.get(Constant.SHIRO_LOGIN_COUNT+username))>=5){
//			opsForValue.set(Constant.SHIRO_IS_LOCK+username, "LOCK");
//			stringRedisTemplate.expire(Constant.SHIRO_IS_LOCK+username, 1, TimeUnit.HOURS);
//		}
//		if ("LOCK".equals(opsForValue.get(Constant.SHIRO_IS_LOCK+username))){
//			throw new DisabledAccountException("由于密码输入错误次数过多，请1小时候再尝试！");
//		}

		// 查询用户信息
		List<UserDO> list = userMapper.list(map);
		UserDO user = null;
		if(list.size()>0){
			user = userMapper.list(map).get(0);
		}


		// 账号不存在
		if (user == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 密码错误
		if (!password.equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
//		opsForValue.set(Constant.SHIRO_LOGIN_COUNT+username, "0");
		return info;
	}

	/**
	 * 手动清空Cache中权限，重新获取，username为你登陆的用户名
	 * @param username
	 */
//	public void removeUserAuthorizationInfoCache(String username) {
//		SimplePrincipalCollection pc = new SimplePrincipalCollection();
//		pc.add(username, super.getName());
//		super.clearCachedAuthorizationInfo(pc);
//	}

}
