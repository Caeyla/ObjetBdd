# ObjetBdd
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>My Framework</title>
    <style>
      body {
        font-family: Arial, sans-serif;
      }
      
      h1 {
        font-size: 2.4rem;
        margin-bottom: 1rem;
      }
      
      h2 {
        font-size: 1.8rem;
        margin-bottom: 0.5rem;
      }
      
      h3 {
        font-size: 1.4rem;
        margin-bottom: 0.5rem;
      }
      
      p {
        font-size: 1.2rem;
        margin-bottom: 1rem;
      }
      
      ul {
        list-style-type: none;
        margin-bottom: 1rem;
      }
      
      li {
        margin-bottom: 0.5rem;
      }
      
      .section {
        margin-bottom: 2rem;
      }
      
      .section-title {
        font-size: 1.8rem;
        margin-bottom: 1rem;
        border-bottom: 1px solid #ccc;
        padding-bottom: 0.5rem;
      }
      
      .sub-section {
        margin-bottom: 1rem;
      }
      
      .sub-section-title {
        font-size: 1.4rem;
        margin-bottom: 0.5rem;
      }
      
      .skill {
        display: inline-block;
        background-color: #f0f0f0;
        color: #666;
        font-size: 1.2rem;
        padding: 0.5rem;
        border-radius: 4px;
        margin-right: 0.5rem;
        margin-bottom: 0.5rem;
      }
      
      .skill-web {
        background-color: #00bcd4;
        color: #fff;
      }
      
      .skill-java {
        background-color: #00796b;
        color: #fff;
      }
      
      .skill-sql {
        background-color: #673ab7;
        color: #fff;
      }
    </style>
  </head>
  <body>
    <h1>My Framework</h1>
    
    <div class="section">
      <h2 class="section-title">About</h2>
      <p>My Framework is a Java 8 library for interacting with relational databases.</p>
    </div>
    
    <div class="section">
      <h2 class="section-title">Feature</h2>
      <ul>
        <li>Object-relational mapping</li>
      </ul>
    </div>
    
    <div class="section">
      <h2 class="section-title">Usage</h2>
      <div class="sub-section">
        <h3 class="sub-section-title">Mapping Tables to Objects  </h3>
        <p>To map a database table to an object  , annotate the object's class with the @TableAnnotation annotation:</p>
      </div>
      <div class="sub-section">
        <h3 class="sub-section-title">Database Operations</h3>
        <p>To perform database operations, use the Session class
