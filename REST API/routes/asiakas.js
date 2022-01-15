const express = require('express');
const router = express.Router();
const asiakas = require('../models/asiakas_model');

router.get('/:id?',
 function(request, response) {
  if (request.params.id) {
    asiakas.getById(request.params.id, function(err, dbResult) {
      if (err) {
        response.json(err);
      } else {
        //palauttaa objectin eikä arrayta
        response.json(dbResult[0]);
      }
    });
  } else {
    asiakas.getAll(function(err, dbResult) {
      if (err) {
        response.json(err);
      } else {
        response.json(dbResult);
      }
    });
  }
});


router.post('/', 
function(request, response) {
  asiakas.add(request.body, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(request.body);
    }
  });
});


router.delete('/:id', 
function(request, response) {
  asiakas.delete(request.params.id, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(dbResult);
    }
  });
});


router.put('/:id', 
function(request, response) {
  asiakas.update(request.params.id, request.body, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(dbResult);
    }
  });
});

router.post('/money_action', 
function(request, response) {
  asiakas.moneyAction(request.body, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(dbResult.affectedRows);
    }
  });
});

module.exports = router;