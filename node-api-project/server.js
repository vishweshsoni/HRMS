var express = require('express'); // Express App include
var http = require('http').Server(app); // http server
var mysql = require('mysql'); // Mysql include
var session = require('express-session');//session handeling
var bodyParser = require("body-parser"); // Body parser for fetch posted data
var app=express();
//var signincontrol=require('./controllers/controllogin')(app);

app.use(session({
     secret: 'ssshhhhh',
     resave: true,
    saveUninitialized: true}));
var sess;//session variable

var connection = mysql.createConnection({ // Mysql Connection
    host : 'localhost',
    user : 'root',
    password : '',
    database : 'login1',
});
app.get('/signin',function(req,res) {
  res.render('signin');
      // var name=req.query.username;
      // var password=req.query.password;

});
app.get('/dashboard1',function(req,res) {
   res.render('dashboard1');
});

var applied_leave_response=undefined;
var leave_policy_response=undefined;
var leave_status_counter=undefined;
var emp_id=undefined;
var policy_tb_sick_leave=undefined;
var counter=0;

app.set('view engine','ejs');//setting up template embeded java script
app.use(express.static('./public'));//it will check the routes automaticall in public folder

var urlencodedParser=bodyParser.urlencoded({ extended: true });
var jsonParser=bodyParser.json();
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json()); // Body parser use JSON data
app.listen(3000,function(){
          console.log("All right ! I am alive at Port 3000.");
        });


// fire sign in controllers
// signincontrol(app);
app.get('/createemp',function(req,res) {
  res.render('createemp');
});
app.get('/employee-details',function(req,res) {
  res.render('createemp');
});
app.post('/dashboard',urlencodedParser,function(req,res) {

   email=req.body.email;
   password=req.body.password;
   connection.query("SELECT * from admin_table WHERE admin_mail=? AND admin_password=?",[req.body.email,req.body.password], function (err,results,fields) {
            if(err){
            console.log(err);
            }else{
                if(results.length>0){
                      //res.render('dashboard');
                      res.render('dashboard1');
                }else {
                    res.render('signin');
                }
              }

            });

});

var emp_unique_id;
app.post('/create2',function(req,res) {

  var username=req.body.username;
  var password=req.body.password;
  var email=req.body.email
  var gender=req.body.gender;
  connection.query("INSERT INTO emp_personal_details set username=?,password=?,email=?,gender=?",[username,password,email,gender],function(err,result,fields) {
    if(err){
      console.log(err);
    }
    else{
        connection.query("SELECT * FROM emp_personal_details ORDER BY id DESC LIMIT 1",function(err,results,fields) {
            if(err){
              console.log(err);
            }

            else{

              req.session.emp_id = results[0].id;
              emp_unique_id = results[0].id;
              res.render('create2',{emp_unique_id});
            }
        });

    }
  });
});

//taking a details of team and salary from adminside
app.post('/create3',function(req,res) {
    connection.query("INSERT INTO emp_team_details set emp_id=?,department=?,post=?,manager_name=?",[emp_unique_id,req.body.Department,req.body.post,req.body.Manager],function(err,results,fields) {
        if(err){
           console.log(err);
        }
        else{
          connection.query("INSERT INTO emp_salary_details set emp_id=?,salary=?,hra=?,da=?,ta=?",[emp_unique_id,req.body.Salary,req.body.HRA,req.body.DA,req.body.TA],function(err,result1,fields) {
            if(err){
              console.log(err);
            }
            else{
              console.log(req.body.Manager);
              connection.query("SELECT * FROM manager_table WHERE manager_name=?",[req.body.Manager],function(err,result2,fields) {
                  if(err){

                    console.log(err);

                  }
                  else{

                    connection.query("UPDATE emp_salary_details set manager_id=? WHERE emp_id=?",[result2[0].manager_id,emp_unique_id],function (err,result3,fields) {
                      if(err){
                         console.log(err);
                      }else{
                        connection.query("UPDATE emp_team_details set manager_id=? WHERE emp_id=?",[result2[0].manager_id,emp_unique_id],function (err,result4,fields) {
                          if(err){
                             console.log(err);
                          }
                          else{
                            res.render('create3');
                          }
                        });
                      }
                    });
                  }
              });

            }
          });
        }
    });

});
app.get('/manage',function (req,res) {
  res.render('report1');
});

app.get('/employeedetails',function(req,res) {
  var obj={};
  connection.query("SELECT * FROM emp_personal_details",function(err,results,fields) {
    if(err){
      console.log("error");
    }
    else{
      obj = {print: results};
       res.render('showempdetails',obj);

    }
  });

});
//reportsalary genration
app.post('/reportsalary',function(req,res) {
    var obj={};
  connection.query("SELECT * FROM  emp_salary_details WHERE salary BETWEEN ? AND ?",[req.body.from,req.body.To],function(err,results,fields) {
      if(err){
         console.log(err);
      }
      else{
        obj = {print: results};
          res.render('reporttable',obj);
      }
  });
});

//leave allotment
var allot_sick,allot_casual,allot_privillage;

app.get('/slot1',function(req,res) {
  allot_sick=5;
  allot_casual=5;
  allot_privillage=5;
  connection.query("INSERT INTO leave_policy_tb SET emp_id=?,sick_leave=?,casual_leave=?,privillage_leave=?",[emp_unique_id,allot_sick,allot_casual,allot_privillage],function(err,result,fields) {
    if(err){
       console.log(err);
    }else{
    res.render('create3');
    }
  });
});
app.get('/slot2',function(req,res) {
  allot_sick=15;
  allot_casual=15;
  allot_privillage=15;
  connection.query("INSERT INTO leave_policy_tb SET emp_id=?,sick_leave=?,casual_leave=?,privillage_leave=?",[emp_unique_id,allot_sick,allot_casual,allot_privillage],function(err,result,fields) {
    if(err){
       console.log(err);
    }else{
      res.render('create3');
    }
  });
});
app.get('/slot3',function(req,res) {
  allot_sick=25;
  allot_casual=25;
  allot_privillage=25;
  connection.query("INSERT INTO leave_policy_tb SET emp_id=?,sick_leave=?,casual_leave=?,privillage_leave=?",[emp_unique_id,allot_sick,allot_casual,allot_privillage],function(err,result,fields) {
    if(err){
       console.log(err);
    }else{
      res.render('create3');
    }
  });
});
app.get('/chart',function (req,res) {
  res.render('chart');
});
//approveleave for the
app.get('/approveleave',function(req,res) {
  var obj={};
  var status="pending";
  connection.query("SELECT * FROM apply_leave WHERE req_status=?",[status],function(err,results,fields) {
    if(err){
      console.log(err);
    }else{
        obj = {print: results};
          res.render('approve',obj);

          console.log(obj);
    }
  });


});
app.post('/approveleave',function (req,res) {
  var obj={};
  var status="approved";
var status1="pending";
  connection.query("UPDATE apply_leave SET req_status=? WHERE emp_id=?",[status,req.body.emp_id],function (err,results,fields) {
    if(err){
       console.log(err);
    }
    else {
      connection.query("SELECT * FROM apply_leave WHERE req_status=?",[status],function(err,results,fields) {
        if(err){
          console.log(err);
        }else{
            obj = {print: results};
              res.render('approve',obj);


        }
      });

    }
  });

});

app.get('/users',function(req,res) {
    var data={
        error:1,
        "Users":""
    };
    connection.query("SELECT * FROM emp_personal_details",function (err,rows,fields) {
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
    var login_credantial={
                  //"Error":false,
                  "Login_Data":""
    };

    connection.query("SELECT * FROM emp_personal_details WHERE username=? AND password=?",[req.body.username,req.body.password],function (err,result,fields) {
        if(err){
           res.json({
             "flag":false,
             "message":"Error in database, please try again!"
            });
        }
        else{
          if (result.length == 1){
            res.json({
              "flag":true,
              "message":"Login successfully done!",
              result
             });

          }
          else {
            res.json({
              "flag":false,
              "message":"No such user or credentials are wrong!",
             });
          }
        }

    });
});
app.post('/signup',function (req,res) {
     connection.query("INSERT INTO emp_personal_details SET username=?,password=?,email=?",[req.body.username,req.body.password,req.body.email],function (err,rows,fields) {
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
       connection.query("SELECT * FROM emp_personal_details WHERE username=?",[req.body.username],function (err,rows,fields) {
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
var applied_data={ status:"unsuccessful",
                  "applied_leave_response":""};


              emp_id=req.body.emp_id;
              var date1 = new Date(req.body.req_from_date);
              var date2 = new Date(req.body.req_to_date);
              var noOfDays = date2.getDate() - date1.getDate();
              console.log(noOfDays);

            var insert_casual=0;
            var insert_sick=0;
              var insert_privillage=0;

              /**
              1. Fetch available leaves of emp_id from remaining_leave_tb
              2. In its response check whether asked leaves can be alloted or not? Meaning req.body.typeOfLeave will give you type of the leave
                 and you will have variables containing balances of each type of leaves of that emp_id
                 req.body will have these parameters 1. emp_id 2. type of leave 3. number of leaves wanted
                 and you will insert these data in leave application table after all above checks. now call me
              */
            connection.query("SELECT * FROM remaining_leave_tb WHERE emp_id=?",[emp_id],function (err,result1,fields) {
                if(err){
                   console.log("Error fetching data from remaining_leave_tb");
                }else{
                     var type_of_leave=req.body.typeOfLeave;
                     if(type_of_leave=="Sick"){
                       var remaining_sick=result1[0].remaining_sick;
                                               if(remaining_sick>=noOfDays){
                                                     var allowed_sick=true;
                                                     insert_sick=noOfDays;
                                                 }else
                                                 {
                                                   allowed_sick=false;
                                                 }
                                               }
                     if(type_of_leave=="Casual"){
                                    var remaining_casual=result1[0].remaining_casual;
                                    if(remaining_casual>=noOfDays){
                                           var allowed_casual=true;
                                           insert_casual=noOfDays;
                                            }else{
                                                allowed_casual=false;
                                            }
                                         }
                     if(type_of_leave=="Privillage")
                     {
                                            var remaining_privilliges=result1[0].remaining_privilliges;
                                            if(remaining_privilliges>=noOfDays){
                                               var allowed_privillage=true;
                                             insert_privillage=noOfDays;

                                            }else{
                                              allowed_privillage=false; }
                                        }
                      if(allowed_sick==true || allowed_casual==true || allowed_privillage==true){
                                               console.log("in");
                                                           connection.query("INSERT INTO apply_leave set emp_id=?,req_sick=?,req_casual=?,req_privillage=?,req_to_date=?,req_from_date=?",[req.body.emp_id,insert_sick,insert_casual,insert_privillage,req.body.req_to_date,req.body.req_from_date],function (err,rows2,fields) {
                                                                                       //  connection.query("SELECT * FROM remaining_leave_tb WHERE emp_id=?",[emp_id],function(err,result1,fields) {
                                                                                        if(err){
                                                                                            res.json({"error":true,
                                                                                                      "message":"error reguarding the mysql to insert the data for apply_leave"});
                                                                                         }
                                                                                        else{
                                                                                           var apply_leave_response=JSON.stringify(rows2);
                                                                                           //res.json(rows);
                                                                                           connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function (err,rows3,fields) {
                                                                                             if(err){
                                                                                                  console.log(err);
                                                                                              }
                                                                                           //  console.log(err);}
                                                                                             else{
                                                                                                applied_data["status"]="successful"
                                                                                                applied_data["applied_leave_response"]=rows3;

                                                                                                res.json(applied_data);
                                                                                             }
                                                                                           });
                                                                                        }
                                                          });
                                                        }
                                                        else{
                                                           res.json({"status":"unsuccessful","tag":"true","message":"you're not allowed"});
                                                        }

                }

            });

           // connection.query("SELECT * FROM remaining_leave_tb WHERE emp_id=?",[emp_id],function(err,result1,fields) {
           //                if(err)
           //                {console.log("error fetching from remaining_leave_tb");}
           //                else{
           //                      var type_of_leave=req.body.typeOfLeave;
           //                      if(type_of_leave==sick){
           //                           var remaining_sick=result1[0].remaining_sick;
           //                           if(remaining_sick>=noOfDays){
           //                               var allowed_sick=true;
           //                               insert_sick=noOfDays;
           //                           }else{
           //                             allowed_sick=false;
           //                           }
           //                      }
           //                      if(type_of_leave==casual){
           //                         var remaining_casual=result1[0].remaining_casual;
           //                         if(remaining_casual>=noOfDays){
           //                            var allowed_casual=true;
           //                            insert_casual=noOfDays;
           //                         }else{
           //
           //                             allowed_casual=false;
           //                         }
           //                      }
           //                      if(type_of_leave==privillage){
           //                          var remaining_privilliges=result1[0].remaining_privilliges;
           //                          if(remaining_privilliges>=noOfDays){
           //                             var allowed_privillage=true;
           //                           insert_privillage=noOfDays;
           //
           //                          }else{
           //                            allowed_privillage=false; }
           //                      }
           //                   if(allowed_sick==true || allowed_casual==true || allowed_privillage==true){
           //
           //                      connection.query("INSERT INTO apply_leave set emp_id=?,req_sick=?,req_casual=?,req_privillage=?,req_to_date=?,req_from_date=?",[req.body.emp_id,insert_sick,insert_casual,insert_privillage,req.body.req_to_date,req.body.req_from_date],function (err,rows2,fields) {
           //                                                  //  connection.query("SELECT * FROM remaining_leave_tb WHERE emp_id=?",[emp_id],function(err,result1,fields) {
           //                                                   if(err){
           //                                                       res.json({"error":true,
           //                                                                 "message":"error reguarding the mysql to insert the data for apply_leave"});
           //                                                    }
           //                                                   else{
           //                                                      var apply_leave_response=JSON.stringify(rows2);
           //                                                      //res.json(rows);
           //                                                      connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function (err,rows3,fields) {
           //                                                        if(err){
           //                                                             console.log(err);
           //                                                         }
           //                                                      //  console.log(err);}
           //                                                        else{
           //
           //                                                           applied_leave_response=rows3;
           //
           //                                                           res.json(applied_leave_response);
           //                                                        }
           //                                                      });
           //                                                   }
           //                     });
           //                   }
           //                   else{
           //                      res.json({"tag":"true","message":"you're not allowed"});
           //                   }
           //                }
            //});





});

app.post('/leave_status_permission',function(req,res) {
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
              // if(counter===0){
              //          counter++;
              //          console.log(counter);0
              // connection.query("INSERT INTO remaining_leave_tb set emp_id=?,policy_id=?,remaining_sick=?,remaining_casual=?,remaining_privilliges=?",[emp_id,policy_id,policy_tb_sick_leave,policy_tb_casual_leave,policy_tb_privillage_leave],function(err,results,fields) {
              //           if(err){
              //            console.log(err);
              //            }else{
              //              console.log("Inserted first initiallisation");
              //             }
              //     });
              // }
              connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function(err,results,fields) {
                  if(err){//console.log("error reguarding the leave selecting");
                             }
                  else{
                    console.log(counter);
                     var apply_leave_sick_leave=result[0].req_sick;
                     var apply_leave_casual_leave=result[0].req_casual;
                     var apply_leave_privillage_leave=result[0].req_privillage;
                      if(policy_tb_sick_leave<apply_leave_sick_leave){
                                   var ammount_sick_allowed=false;
                      }else if(policy_tb_casual_leave<apply_leave_casual_leave){
                         var ammount_casual_allowed=false;
                      }else if(policy_tb_privillage_leave<apply_leave_privillage_leave){
                               var ammount_privillage_allowed=false;
                      }else{

                        ammount_sick_allowed=true;
                        ammount_casual_allowed=true;
                        ammount_privillage_allowed=true;
                  }
                  if(ammount_sick_allowed==false && ammount_casual_allowed==false && ammount_privillage_allowed==false)
                  {
                       res.json({"error":true,"message":"You dont have this amount of leave"});
                  }
                  else{
                      res.json({"message":"wait for the manager approval"});
                  }
              }
            });



        }
   });


});
app.post("/leave_status",function(req,res) {

  var data={

      "emp_status":""
  };
  emp_id=req.body.emp_id;
  connection.query("SELECT * FROM apply_leave WHERE emp_id=?",[emp_id],function(err,result,fields) {
    if(err){
         res.json({"message":"error fetching the data"});
    }else{
        data["emp_status"]=result;
        res.json(data);
    }
  });

});
