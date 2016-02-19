package stone.sample;

import java.util.ArrayList;

public class EntryActivity {
	public EntryActivity(){
		filters = new ArrayList();
	}
	public EntryActivity(String name){
		super();
		this.Name  = name;
	}
	private String Name;
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	private boolean isMain = false;
	public boolean isMain() {
		return isMain;
	}
//	public void setMain(boolean isMain) {
//		this.isMain = isMain;
//	}
	private Object Data;
	public Object getData() {
		return Data;
	}
	public void setData(Object data) {
		Data = data;
	}
	
	private ArrayList<EntryIntentFilter> filters;
//	public EntryIntentFilter[] getFilters() {
//		return filters;
//	}
//	public void setFilters(EntryIntentFilter[] filters) {
//		this.filters = filters;
//	}
	public void addFilterByName(String name){
		
		int count = this.filters.size();
		EntryIntentFilter filter = new EntryIntentFilter();
		filter.setName(name);
		this.filters.add(filter);
		if(name.equals("android.intent.action.MAIN")){
			this.isMain = true;
		}
		
	}
	public ArrayList<EntryIntentFilter> getFilters() {
		return filters;
	}
	public void setFilters(ArrayList<EntryIntentFilter> filters) {
		this.filters = filters;
	}
	public void setAsMainActivity(){
		this.isMain = true;
	}
	public void deleteAsMainActivity(){
		this.isMain = false;
	}
}
