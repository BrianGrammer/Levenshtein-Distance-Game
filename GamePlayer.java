public class GamePlayer {
	
	private String name, password;
	private int tries, opt, success, rounds;
	
	
	public GamePlayer(String name) {
		this.name = name;
		this.password = null;
		this.rounds = 0;
		this.success = 0;
		this.opt = 0;
		this.tries = 0;
	}
	
	public GamePlayer(String name, String password) {
		this.name = name;
		this.password = password;
		this.rounds = 0;
		this.success = 0;
		this.opt = 0;
		this.tries = 0;
	}
	
	public GamePlayer(String name, String password, int rounds, int success, int opt, int tries) {
		this.name = name;
		this.password = password;
		this.rounds = rounds;
		this.success = success;
		this.opt = opt;
		this.tries = tries;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPass(String password) {
		this.password = password;
	}
	
	public void success(int tries, int opt) {
		this.tries += tries; 
		this.opt += opt;
		success++;
		rounds++;
	}
	
	public void failure() {
		rounds++;
	}
	
	public boolean equals(GamePlayer G) {
		if(G.password.equals(this.password) && G.name.equals(this.name))
			return true;
		else
			return false;
	}
	
	
	public String toString() {
		String s  = ("\tName: " + name+ "\n"
				+ "\tRounds: "+ rounds+"\n"
				+ "\tSuccesses: "+ success+"\n"
				+ "\tFailures: "+ Math.abs((rounds-success))+"\n");
				if(opt==0) 
					s+="\tRatio (successes only): "+ 1.0 ;
				else
					if(opt>0) 
						s+="\tRatio (successes only): "+ ((double)tries/(double)opt) +"\n";
		return "\n"+s;
	}
	
	public String toStringFile() {
		String s = name+","+password+"," +rounds+","+success+","+opt+","+tries +"\n";
		return s;
	}
}