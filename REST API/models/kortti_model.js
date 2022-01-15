const db = require('../database');
var bcrypt = require('bcryptjs');

const kortti = {
  getById: function(id, callback) {
    return db.query('select * from kortti where korttinumero=?', [id], callback);
  },
  getAll: function(callback) {
    return db.query('select * from kortti', callback);
  },
  add: function(kortti, callback) {
    return db.query(
      'insert into kortti (korttinumero, pinkoodi, Asiakas_tunnus) values(?,?,?)', 
      [kortti.korttinumero, kortti.pinkoodi, kortti.Asiakas_tunnus],
      callback
    );
  },
  delete: function(id, callback) {
    return db.query('delete from kortti where korttinumero=?', [id], callback);
  },
  update: function(request, callback) 
  {
      bcrypt.hash(request.pin, 10, function(err, pinkoodi)
      {
        console.log(this.pinkoodi);
        db.query(
        'update kortti set pinkoodi=? where korttinumero=?',
        [this.pinkoodi, request.kortti],
        callback
        );
        
      });
  }
};
module.exports = kortti;