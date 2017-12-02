var app = require('express')(); // Express App include
var http = require('http').Server(app); // http server
var mysql = require('mysql'); // Mysql include
var bodyParser = require("body-parser"); // Body parser for fetch posted data
var connection = mysql.createConnection({ // Mysql Connection
    host : 'localhost',
    user : 'root',
    password : '',
    database : 'login1',
});
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json()); // Body parser use JSON data
app.listen(3000,function(){
          console.log("All right ! I am alive at Port 3000.");
        });
app.get('/users',function(req,res) {
    var data={
        error:1,
        "Users":""
    };
    connection.query("SELECT * FROM log",function (err,rows,fields) {
    if(rows.length!=0){
         data["error"]=0;
          data["Users"]=rows;
          res.json(data);
        }else{
           data["Users"]='no users found ..';
           res.json(data);
        }
    });
});
app.post('/login',function(req,res) {
    connection.query("SELECT * FROM log WHERE username=?",[req.body.username],function (err,rows,fields) {
        if(err){
           res.json({"error":true,
                     "message":"error reguarding the mysql"});
                      }
                      else{
                         res.json(rows);
                      }

    });
});
app.post('/signup',function (req,res) {
     connection.query("INSERT INTO log SET username=?,password=?,email=?",[req.body.username,req.body.password,req.body.email],function (err,rows,fields) {
       if(err){
          res.json({"error":true,
                      "message":"error regarding the mysql query"});
       }
       else{
           res.json({"error":false,
                     "message":"user added successfully"});
       }
     });
     app.post('/user',function (req,res) {
       connection.query("SELECT * FROM log WHERE username=?",[req.body.username],function (err,rows,fields) {
           if(err){
              res.json({"error":true,
                        "message":"error reguarding the mysql"});
                         }
                         else{
                            res.json(rows);
                         }

       });

});
