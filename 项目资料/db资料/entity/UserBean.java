package entity;
/**
 * 用户
 */
public class UserBean {
	private int u_id;
	private String u_name;
	private String u_password;
	private String u_gender;
	private int u_age;
	private String u_phone;
	private String u_address;
	private String u_auth;//权限
	
	public int getU_id() {
		return u_id;
	}
	public void setU_id(int uId) {
		u_id = uId;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String uName) {
		u_name = uName;
	}
	public String getU_password() {
		return u_password;
	}
	public void setU_password(String uPassword) {
		u_password = uPassword;
	}
	public String getU_gender() {
		return u_gender;
	}
	public void setU_gender(String uGender) {
		u_gender = uGender;
	}
	public int getU_age() {
		return u_age;
	}
	public void setU_age(int uAge) {
		u_age = uAge;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String uPhone) {
		u_phone = uPhone;
	}
	public String getU_address() {
		return u_address;
	}
	public void setU_address(String uAddress) {
		u_address = uAddress;
	}
	public String getU_auth() {
		return u_auth;
	}
	public void setU_auth(String uAuth) {
		u_auth = uAuth;
	}
	
}
