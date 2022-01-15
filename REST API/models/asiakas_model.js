const db = require('../database');

const asiakas = {
  getById: function(id, callback) {
    return db.query('select * from asiakas where tunnus=?', [id], callback);
  },
  getAll: function(callback) {
    return db.query('select * from asiakas', callback);
  },
  add: function(asiakas, callback) {
    return db.query(
      'insert into asiakas (Enimi,Snimi, lahiosoite, puhelinnumero) values(?,?,?,?)', 
      [asiakas.Enimi, asiakas.Snimi, asiakas.lahiosoite, asiakas.puhelinnumero],
      callback
    );
  },
  delete: function(id, callback) {
    return db.query('delete from asiakas where tunnus=?', [id], callback);
  },
  update: function(id, asiakas, callback) {
    return db.query(
      'update asiakas set Enimi=?, Snimi=?, lahiosoite=?, puhelinnumero=? where tunnus=?',
      [asiakas.Enimi, asiakas.Snimi, asiakas.lahiosoite, asiakas.puhelinnumero, id],
      callback
    );
  },

  moneyAction: function(procedure_params, callback) {
    return db.query(
      'CALL money_action (?,?)',
      [procedure_params.id, procedure_params.amount],
      callback
    );
  }
};
module.exports = asiakas;