package mmt.uit.placehelper.models;

public class Category {
	
	private int id;

	private int name;

	private int imgID;
	
	private String types;
	
	public Category (){
		
	}
   
    public Category(int id, int name) {
        this.id = id;
    	this.name = name;
    }

   

	public int getName() {
        return name;
    }

    public void setName(int i) {
        this.name = i;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    
    public int getId (){
    	return id;
    }

	public int getImgID() {
		return imgID;
	}

	public void setImgID(int imgID) {
		this.imgID = imgID;
	}
	
	public Category copy(){
		Category copy = new Category();
		copy.id = id;
		copy.imgID = imgID;
		copy.name = name;
		return copy;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Name: "+name +"\n");
		sb.append("ID: "+id);
		sb.append("\n");
		sb.append("IMG: "+imgID);
		sb.append("\n");
		sb.append("Types: "+types);
		return sb.toString();
	}

	/**
	 * @return the types
	 */
	public String getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(String types) {
		this.types = types;
	}
}
