package net.sparkzz.servercontrol.player;

public class User {
	private boolean invSee = false;
	
	public boolean isInvSee() {
		return invSee;
	}
	
	public void setInvSee(final boolean set) {
		invSee = set;
	}
	
	static User user = new User();
	
	public static User getUser() {
		return user;
	}
}
