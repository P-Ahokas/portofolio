const db = require('../database');

const tili = {
  getById: function(id, callback) {
    return db.query('select * from tili where tilinumero=?', [id], callback);
  },
  getAll: function(callback) {
    return db.query('select * from tili', callback);
  },
  add: function(tili, callback) {
    return db.query(
      'insert into tili (tilinumero, tilin_saldo) values(?,?)', 
      [tili.tilinumero, tili.tilin_saldo],
      callback
    );
  },
  delete: function(id, callback) {
    return db.query('delete from tili where tilinumero=?', [id], callback);
  },
  update: function(id, tili, callback) {
    return db.query(
      'update tili set tilin_saldo=? where tilinumero=?',
      [tili.tilin_saldo, id],
      callback
    );
  }
};
module.exports = tili;