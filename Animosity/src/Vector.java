public class Vector { 
	//VECTOR MATH CLASS
    float x;
    float y;
    
    public Vector(float x_,float y_){
    	x=x_;
    	y=y_;
    }
    void add(Vector v){
    	x=x+v.x;
    	y=y+v.y;
    }
    void sub(Vector v) {
        x = x - v.x;
        y = y - v.y;
    }
    void mult(float n) {
    	x = x * n;
    	y = y * n;
    }
    void normalize() {
    	float m = mag();
    	if (m != 0) {
    		div(m);
    	}
    }
    void setMag(float x){
    	this.normalize();
    	this.mult(x);
    }
    void div(float n) {
    	x = x / n;
    	y = y / n;
    }
    void limit(float max){
    	if (Math.abs(this.mag())>max){
    		this.normalize();
    		this.mult(max);
    	}
    	
    }
    float mag(){
    	float res= (float) Math.sqrt((x*x)+(y*y));
    	return res;
    }
	@Override
	public String toString() {
		return "Vector [x=" + x + ", y=" + y + "]";
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	static float dist(Vector x,Vector y){
		float res=0;
		res=(float)Math.sqrt(Math.pow((y.x-x.x), 2)+(Math.pow((y.y-x.y),2)));
		return res;
		
	}
    static Vector sub(Vector v, Vector c){
    	Vector res=new Vector(0,0);
    	res.x=v.x-c.x;
    	res.y=v.y-c.y;
    	return res;
    	
    }

}