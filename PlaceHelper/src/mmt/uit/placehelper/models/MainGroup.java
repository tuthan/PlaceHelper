package mmt.uit.placehelper.models;

import java.util.ArrayList;
import java.util.List;

public class MainGroup extends Category{
	private List<Child> child = new ArrayList<Child>();

	public MainGroup(){
		
	}
	
	public MainGroup(Child child){
		this.child.add(child);
	}
	
	public MainGroup(List<Child> child){
		this.child = child;
	}
	public List<Child> getChild() {
		return child;
	}

	public void setChild(List<Child> child) {
		this.child = child;
	}
	
	public void addChild (Child child){
		this.child.add(child);
	}
	
	@Override
	public MainGroup copy() {
		// TODO Auto-generated method stub
		MainGroup copy = new MainGroup();
		copy.setId(getId());
		copy.setImgID(getImgID());
		copy.setName(getName());
		copy.child = child;
		return copy;
	}
}
