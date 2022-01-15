const express = require('express');
const router = express.Router();
const kortti = require('../models/kortti_model');

router.get('/:id?',
 function(request, response) {
  if (request.params.id) {
    kortti.getById(request.params.id, function(err, dbResult) {
      if (err) {
        response.json(err);
      } else {
        //palauttaa objectin eikä arrayta
        response.json(dbResult[0]);
      }
    });
  } else {
    kortti.getAll(function(err, dbResult) {
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
  kortti.add(request.body, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(request.body);
    }
  });
});


router.delete('/:id', 
function(request, response) {
  kortti.delete(request.params.id, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      response.json(dbResult);
    }
  });
});


router.put('/', 
function(request, response) {
  kortti.update(request.body, function(err, dbResult) {
    if (err) {
      response.json(err);
    } else {
      console.log(request.body);
      response.json(dbResult);
    }
  });
});

module.exports = router;