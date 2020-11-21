/**
 * @author
 * @create 2020-11-21 11:41
 */
public class Planet {

    double xxPos;
    double yyPos;
    double xxVel;
    double yyVel;
    double mass;
    String imgFileName;

    @Override
    public String toString() {
        return "Planet{" +
                "xxPos=" + xxPos +
                ", yyPos=" + yyPos +
                ", xxVel=" + xxVel +
                ", yyVel=" + yyVel +
                ", mass=" + mass +
                ", imgFileName='" + imgFileName + '\'' +
                '}';
    }

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet p){
        this.xxPos = p.getXxPos();
        this.yyPos = p.getYyPos();
        this.xxVel = p.getXxVel();
        this.yyVel = p.getYyVel();
        this.mass = p.getMass();
        this.imgFileName = p.getImgFileName();
    }

    public Planet() {
    }

    public double getXxPos() {
        return xxPos;
    }

    public void setXxPos(double xxPos) {
        this.xxPos = xxPos;
    }

    public double getYyPos() {
        return yyPos;
    }

    public void setYyPos(double yyPos) {
        this.yyPos = yyPos;
    }

    public double getXxVel() {
        return xxVel;
    }

    public void setXxVel(double xxVel) {
        this.xxVel = xxVel;
    }

    public double getYyVel() {
        return yyVel;
    }

    public void setYyVel(double yyVel) {
        this.yyVel = yyVel;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public String getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String imgFileName) {
        this.imgFileName = imgFileName;
    }

    public double calcDistance(Planet p){
        double x2 = (p.getXxPos() - this.xxPos) * (p.getXxPos() - this.xxPos);
        double y2 = (p.getYyPos() - this.yyPos) * (p.getYyPos() - this.yyPos);
        return Math.sqrt(x2 + y2);
    }

    public double calcForceExertedBy(Planet p){
        double G = 6.67e-11;
        double distance = this.calcDistance(p);
        return G * this.mass * p.getMass() / (distance * distance);
    }

    public double calcForceExertedByX(Planet p){
        double dx = p.getXxPos() - this.xxPos;
        double distance = this.calcDistance(p);
        double force = this.calcForceExertedBy(p);
        return force * dx / distance;
    }

    public double calcForceExertedByY(Planet p){
        double dy = p.getYyPos() - this.yyPos;
        double distance = this.calcDistance(p);
        double force = this.calcForceExertedBy(p);
        return force * dy / distance;
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double force = 0;
        for(Planet p: planets){
            if(!p.equals(this))
                force += this.calcForceExertedByX(p);
        }
        return force;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double force = 0;

        for(Planet p: planets){
            if(!this.equals(p))
                force += this.calcForceExertedByY(p);
        }
        return force;
    }

    public void update(double dt, double forceX, double forceY){
        double ax = forceX / this.mass;
        double ay = forceY / this.mass;
        this.xxVel = this.xxVel + ax*dt;
        this.yyVel = this.yyVel + ay*dt;

        this.xxPos = this.xxPos + this.xxVel * dt;
        this.yyPos = this.yyPos + this.yyVel * dt;
    }

    public void draw(){
        String filename  = "images\\"+this.imgFileName;
        StdDraw.picture(this.xxPos,this.yyPos,filename);
    }
}
