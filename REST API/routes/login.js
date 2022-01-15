const express = require('express');
const router = express.Router();
const bcrypt = require('bcryptjs');
const login = require('../models/login_model');

router.post('/', 
  function(request, response) 
  {
    if(request.body.kortti && request.body.pin)
    {
      const username = request.body.kortti;
      const password = request.body.pin;
        login.checkPassword(username, function(dbError, dbResult) 
        {
          if(dbError)
          {
            response.json(dbError);
          }
          else
          {
            if (dbResult.length > 0) 
            {
              bcrypt.compare(password,dbResult[0].password, function(err,compareResult) 
              {
                if(compareResult) 
                {
                  console.log("success");
                  response.send(true);
                }
                else 
                {
                    console.log("wrong password");
                    response.send(false);
                }			
              }
              );
            }
            else
            {
              console.log("user does not exists");
              response.send(false);
            }
          }
        }
        );
    }
    else
    {
      console.log("username or password missing");
      response.send(false);
    }
  }
);

module.exports=router;