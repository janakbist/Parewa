package com.example.parewa.data.model;

public class User {
 String Email, Password, Firstname,Lastname,Gender,coverPhoto;

 public User(String email, String password, String firstname, String lastname, String gender, String coverPhoto) {
  Email = email;
  Password = password;
  Firstname = firstname;
  Lastname = lastname;
  Gender = gender;
  this.coverPhoto = coverPhoto;
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
}
