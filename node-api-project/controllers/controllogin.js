var express=require("express");
 var bodyParser=require("body-parser");
 var app=express();
 app.use(bodyParser.urlencoded({ extended: true }));
 app.use(bodyParser.json())
module.exports=function(app){

    app.post('/signin',app,function(req,res) {
      res.render('signin');
    console.log(req.query);

    });

};
