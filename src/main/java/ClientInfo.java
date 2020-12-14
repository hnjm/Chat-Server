import java.io.Serializable;
import java.util.ArrayList;

public class ClientInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String messageSent;
	
	ArrayList<Integer> clientLists;
	
	ClientInfo()
	{
		clientLists = new ArrayList<>();
		
	}
	
	public void setClientList(ArrayList<Integer> temp)
	{
		clientLists = new ArrayList<>();
		this.clientLists.addAll(temp);
	}
	
	
	public void setMessage(String temp)
	{
		this.messageSent = temp;
	}
	
	public ArrayList<Integer> getClientLists()
	{
		return this.clientLists;
	}
	
	public String getMessage()
	{
		return this.messageSent;
	}
	
	public void removeClient(Integer temp)
	{
		this.clientLists.remove(temp);
	}
}
