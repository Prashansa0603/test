package com.examplecategory.category.entity;

import jakarta.persistence.*;

@Entity
public class Product {
   @Id
   private int P_id;
   private String P_name;

   private String P_description;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id")
   private Category category;

   public Product(int p_id, String p_name, String p_description, Category category) {
      P_id = p_id;
      P_name = p_name;
      P_description = p_description;
      this.category = category;
   }

   public Product() {
   }

   public int getP_id() {
      return P_id;
   }

   public void setP_id(int p_id) {
      P_id = p_id;
   }

   public String getP_name() {
      return P_name;
   }

   public void setP_name(String p_name) {
      P_name = p_name;
   }

   public String getP_description() {
      return P_description;
   }

   public void setP_description(String p_description) {
      P_description = p_description;
   }

   public Category getCategory(Category category) {
      return this.category;
   }

   public void setCategory(Category category) {
      this.category = category;
   }
}
