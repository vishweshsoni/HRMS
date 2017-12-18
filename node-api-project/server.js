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
var applied_leave_response=undefined;
var leave_policy_response=undefined;
var leave_status_counter=undefined;
var emp_id=undefined;
var policy_tb_sick_leave=undefined;
var counter=0;
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
app.post('/leave_policy',function(req,res) {
              connection.query("INSERT INTO leave_policy_tb set sick_leave=?,casual_leave=?,privillage_leave=?,holidays_leave=?",[req.body.sick_leave,req.body.casual_leave,req.body.privillage_leave,req.body.holidays_leave],function (err,rows,fields) {
                                                 if(err){
                                                                res.json({"error":true,
                                                                   "message":"error reguarding the"});}
                                                      //  console.log(err);}//checking the error for terminal
                                                                  else{
                                                                     res.json(rows);

                                                                  }

            });

  });
app.post('/apply_leave',function (req,res) {
              emp_id=req.body.emp_id;
              /**
              1. Fetch available leaves of emp_id from remaining_leave_tb
              2. In its response check whether asked leaves can be alloted or not? Meaning req.body.typeOfLeave will give you type of the leave
                 and you will have variables containing balances of each type of leaves of that emp_id
                 req.body will have these parameters 1. emp_id 2. type of leave 3. number of leaves wanted
                 and you will insert these data in leave application table after all above checks. now call me
              */
          connection.query("INSERT INTO apply_leave set emp_id=?,req_sick=?,req_casual=?,req_privillage=?,req_to_date=?,req_from_date=?",[req.body.emp_id,req.body.req_sick,req.body.req_casual,req.body.req_privillage,req.body.req_to_date,req.body.req_from_date],function (err,rows,fields) {
                                        if(err){
                                            res.json({"error":true,
                                                      "message":"error reguarding the mysql"});
                                         }
                                      //  console.log(err);}
                                        else{
                                           var apply_leave_response=JSON.stringify(rows);
                                           //res.json(rows);
                                           connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function (err,rows,fields) {
                                             if(err){
                                                  console.log(err);
                                              }
                                           //  console.log(err);}
                                             else{

                                                applied_leave_response=rows;

                                                res.json(applied_leave_response);
                                             }
                                           });
                                        }
          });



});
app.post('/leave_status',function(req,res) {
  emp_id=req.body.emp_id;
  policy_id=req.body.policy_id;
    connection.query("SELECT sick_leave,casual_leave,privillage_leave from leave_policy_tb WHERE policy_id=?",[policy_id],function(err,result,fields){
        if(err){
           console.log(err);
        }else{
           policy_tb_sick_leave=result[0].sick_leave;
           var policy_tb_casual_leave=result[0].casual_leave;
           var policy_tb_privillage_leave=result[0].privillage_leave;
           console.log(counter);
//have ahiya check kar ke leaves j joie e and sick leave ni andar che ke nai etli leaves and ahiya niche j insert ni query lakhi de etle kam puru taru
              if(counter===0){
                       counter++;
                       console.log(counter);
              connection.query("INSERT INTO remaining_leave_tb set emp_id=?,policy_id=?,remaining_sick=?,remaining_casual=?,remaining_privilliges=?",[emp_id,policy_id,policy_tb_sick_leave,policy_tb_casual_leave,policy_tb_privillage_leave],function(err,results,fields) {
                        if(err){
                         console.log(err);
                         }else{
                           console.log("Inserted first initiallisation");
                          }
                  });
              }
              connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function(err,results,fields) {
                  if(err){console.log("error reguarding the leave selecting");}
                  else{
                    console.log(counter);
                     var apply_leave_sick_leave=result[0].req_sick;
                     var apply_leave_casual_leave=result[0].req_casual;
                     var apply_leave_privillage_leave=result[0].req_privillage;
                      if(policy_tb_sick_leave<apply_leave_sick_leave){
                         res.json({"error":true,"message":"You dont have this amount of leave"});
                      }
                      else{
                        res.json({"message":"wait for the manager approval"});
                      }
                       if(policy_tb_casual_leave<apply_leave_casual_leave){
                         res.json({"error":true,"message":"You dont have this amount of leave"});
                      }
                      else{
                        res.json({"message":"wait for the manager approval"});}
                       if(policy_tb_privillage_leave<apply_leave_privillage_leave){
                         res.json({"error":true,"message":"You dont have this amount of leave"});
                      }else{
                        res.json({"message":"wait for the manager approval"});}
                  }
              });



        }
   });


});
