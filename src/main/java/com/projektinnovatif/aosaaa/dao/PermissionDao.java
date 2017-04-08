package com.projektinnovatif.aosaaa.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.projektinnovatif.aosaaa.model.Permission;
import com.projektinnovatif.aosaaa.model.Permissioninfo;

@Repository
public class PermissionDao extends BaseDao<Permission> {

	public PermissionDao() {
		super(Permission.class);
	}

	
	public boolean checkAction (Long accountid, String actionString) {
		Session session = this.sessionFactory.getCurrentSession();
		Permission permission = (Permission) session
				.createQuery("from Permission p where p.accountid=:accountid and p.permissionstring=:actionstring and p.startdt <= NOW() and p.expireddt >= NOW() and p.isactive=:isactive")
				.setLong("accountid", accountid)
				.setString("actionstring", actionString)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		if (permission != null) return true;
		else return false;
	}
	
	
	/**
	 * Check whether the account has sufficient permissions to perform an action on a resource
	 * @param accountid
	 * @param actionString
	 * @param resourceid
	 * @return
	 * true: Account has the permissions
	 * false: Otherwise
	 */
	public boolean checkActionOnResource (Long accountid, String actionString, Long resourceid) {
		Session session = this.sessionFactory.getCurrentSession();
		Permission permission = (Permission) session
				.createQuery("from Permission p where p.accountid=:accountid and p.permissionstring=:actionstring and resourceid=:resourceid and p.startdt <= NOW() and p.expireddt >= NOW() and p.isactive=:isactive")
				.setLong("accountid", accountid)
				.setString("actionstring", actionString)
				.setLong("resourceid", resourceid)
				.setByte("isactive", (byte)1)
				.uniqueResult();
		if (permission != null) return true;
		else return false;
	}
	
	
	public List<Long> getPermittedResourcesForAccount (Long accountid, String permissionString) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> resourceList = session
				.createQuery("select p.resourceid from Permission p where p.accountid=:accountid and p.permissionstring=:permissionstring and p.isactive=:isactive")
				.setString("permissionstring", permissionString)
				.setLong("accountid", accountid)
				.setByte("isactive", (byte)1)
				.list();
		return resourceList;
	}
	
	
	public boolean addRolePermissions (Long accountid, Long roleid, Long resourceid) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 100);
		return addRolePermissions (accountid, null, roleid, resourceid, cal.getTime());
	}
	
	
	public boolean addRolePermissions (Long accountid, Long subscriptionid, Long roleid, Long resourceid, Date expireddt) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Permissioninfo> permissionInfoList = session
				.createQuery("select p from Permissioninfo p where p.roleid=:roleid and p.isactive=:isactive")
				.setLong("roleid", roleid)
				.setByte("isactive", (byte)1)
				.list();
		for (Permissioninfo permissionInfoItem: permissionInfoList) {
			Permission permission = new Permission();
			permission.setAccountid(accountid);
			permission.setSubscriptionid(subscriptionid);
			permission.setPermissionstring(permissionInfoItem.getTitle());
			permission.setIsactive((byte)1);
			permission.setStartdt(new Date());
			permission.setExpireddt(expireddt);
			if (permissionInfoItem.getIsobjectspecific() == (byte)1) {
				permission.setResourceid(resourceid);
			}
			this.add(permission);
		}
		return true;
	}
	
	
	public boolean addRolePermissions (Long accountid, Long roleid) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 100);
		return addRolePermissions (accountid, null, roleid, cal.getTime());
	}
	
	
	public boolean addRolePermissions (Long accountid, Long subscriptionid, Long roleid, Date expireddt) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> permissionList = session
				.createQuery("select p.title from Permissioninfo p where p.roleid=:roleid and p.isobjectspecific=:isobjectspecific and p.isactive=:isactive")
				.setLong("roleid", roleid)
				.setByte("isobjectspecific", (byte)0)
				.setByte("isactive", (byte)1)
				.list();
		for (String permissionItem: permissionList) {
			Permission permission = new Permission();
			permission.setAccountid(accountid);
			permission.setSubscriptionid(subscriptionid);
			permission.setPermissionstring(permissionItem);
			permission.setStartdt(new Date());
			permission.setExpireddt(expireddt);
			permission.setIsactive((byte)1);
			this.add(permission);
		}
		return true;
	}
	
	
	public boolean removeRolePermissionsForAccount (Long accountid, Long roleid) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<String> permissionList = session
				.createQuery("select p.title from Permissioninfo p where p.roleid=:roleid and p.isobjectspecific=:isobjectspecific and p.isactive=:isactive")
				.setLong("role", roleid)
				.setByte("isobjectspecific", (byte)0)
				.setByte("isactive", (byte)1)
				.list();
		session.createQuery("update Permission p set p.isactive=:isactive where p.accountid=:accountid and p.permissionstring in (:permissionlist)")
		.setByte("isactive", (byte)0)
		.setLong("accountid", accountid)
		.setParameterList("permissionlist", permissionList)
		.executeUpdate();
		return true;
	}
	
	
	public boolean removeAllPermissionsForAccount (Long accountid) {
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("delete Permission p where p.accountid=:accountid")
		.setLong("accountid", accountid)
		.executeUpdate();
		return true;
	}
	
	
	public boolean expireAllPermissionsForItem (String itemtype, Long itemid) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> idList = session
				.createQuery("select p.id from Permission p where p.permissionstring like :itemtype and p.resourceid=:itemid and p.isactive=:isactive")
				.setString("itemtype", itemtype + ":%")
				.setLong("itemid", itemid)
				.setByte("isactive", (byte)1)
				.list();
		session.createQuery("update Permission p set p.isactive=:isactive where p.id in (:idlist)")
				.setByte("isactive", (byte)0)
				.setParameterList("idlist", idList)
				.executeUpdate();
		return true;
	}
	
	
	public boolean expireAllAccountPermissionsForItem (Long accountid, String itemtype, Long itemid) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Long> idList = session
				.createQuery("select p.id from Permission p where p.accountid=:accountid and p.permissionstring like :itemtype and p.resourceid=:itemid and p.isactive=:isactive")
				.setLong("accountid", accountid)
				.setString("itemtype", itemtype + ":%")
				.setLong("itemid", itemid)
				.setByte("isactive", (byte)1)
				.list();
		session.createQuery("update Permission p set p.isactive=:isactive where p.id in (:idlist)")
				.setByte("isactive", (byte)0)
				.setParameterList("idlist", idList)
				.executeUpdate();
		return true;
	}
	
	public boolean expireSpecificAccountPermissionForItem (Long accountid, String permissionstring, Long resourceid) {
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("update Permission p set p.isactive=:isactivenew where p.accountid=:accountid and p.permissionstring=:permissionstring and p.resourceid=:resourceid and p.isactive=:isactiveold")
				.setLong("accountid", accountid)
				.setString("permissionstring", permissionstring)
				.setLong("resourceid", resourceid)
				.setByte("isactiveold", (byte)1)
				.setByte("isactivenew", (byte)0)
				.executeUpdate();
		return true;
	}
	
	public boolean expireSpecificPermissionForItem (String permissionstring, Long resourceid) {
		Session session = this.sessionFactory.getCurrentSession();
		session.createQuery("update Permission p set p.isactive=:isactivenew where p.permissionstring=:permissionstring and p.resourceid=:resourceid and p.isactive=:isactiveold")
				.setString("permissionstring", permissionstring)
				.setLong("resourceid", resourceid)
				.setByte("isactiveold", (byte)1)
				.setByte("isactivenew", (byte)0)
				.executeUpdate();
		return true;
	}
	
}