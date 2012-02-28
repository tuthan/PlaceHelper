package mmt.uit.placehelper.models;

public class Category {
	
	private int id;

	private String name;

	private String imgID;
	
	private String types;
	
	public Category (){
		
	}
   
    public Category(int id, String name) {
        this.id = id;
    	this.name = name;
    }

   

	public String getName() {
        return name;
    }

    public void setName(String i) {
        this.name = i;
    }
    
    public void setId(int id){
    	this.id = id;
    }
    
    public int getId (){
    	return id;
    }

	public String getImgID() {
		return imgID;
	}

	public void setImgID(String imgID) {
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
