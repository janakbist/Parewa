package com.example.parewa.data.model;

public class User {
 private int followerCount;
 public String getUserId() {
  return userId;
 }

 public void setUserId(String userId) {
  this.userId = userId;
 }

 String userId;
 String Email;
 String Password;
 String Firstname;
 String Lastname;
 String Gender;
 String coverPhoto;
 String profile;

 public User(String email, String password, String firstname, String lastname, String gender, String coverPhoto,String profile) {
  Email = email;
  Password = password;
  Firstname = firstname;
  Lastname = lastname;
  Gender = gender;
  this.coverPhoto = coverPhoto;
  this.profile = profile;
 }

 public User() {

 }

 public String getEmail() {
  return Email;
 }

 public void setEmail(String email) {
  Email = email;
 }

 public String getPassword() {
  return Password;
 }

 public void setPassword(String password) {
  Password = password;
 }

 public String getFirstname() {
  return Firstname;
 }

 public void setFirstname(String firstname) {
  Firstname = firstname;
 }

 public String getLastname() {
  return Lastname;
 }

 public void setLastname(String lastname) {
  Lastname = lastname;
 }

 public String getGender() {
  return Gender;
 }

 public void setGender(String gender) {
  Gender = gender;
 }

 public String getCoverPhoto() {
  return coverPhoto;
 }

 public void setCoverPhoto(String coverPhoto) {
  this.coverPhoto = coverPhoto;
 }

 public String getProfile() {
  return profile;
 }

 public void setProfile(String profile) {
  this.profile = profile;
 }

 public int getFollowerCount() {
  return followerCount;
 }

 public void setFollowerCount(int followerCount) {
  this.followerCount = followerCount;
 }
}
