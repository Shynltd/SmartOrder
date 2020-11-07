// let table = require('../model/table');
//
// module.exports.getListMenu = async (req, res) => {
//     let listTable = await table.find();
//     res.render('table/listTable', {listTable});
// }
// module.exports.getCreate = (req, res) => {
//     res.render('table/createTable', {err: false});
// }
//
// module.exports.postCreate = async (req, res) => {
//     let tableCode = req.body.number;
//     let tableSeats = req.body.seats;
//     if (tableCode.length === 0) {
//
//     } else if (tableSeats.length === 0) {
//
//     } else {
//         let addTable = await table.create({tableCode, tableSeats}).catch(err => {
//             res.code(404);
//         });
//         if (addTable) {
//             res.redirect('/tables');
//         }
//     }
// }
// module.exports.getUpdate = async (req, res) => {
//     res.render('table/updateTable', {err: false});
// }
//
// module.exports.postUpdate = async (req, res) => {
//     let tableCode = req.body.number;
//     let tableSeats = req.body.seats;
//     if (tableCode.length === 0) {
//
//     } else if (tableSeats.length === 0) {
//
//     } else {
//         let updateTable = await table.create({tableCode, tableSeats}).catch(err => {
//             res.code(404);
//         });
//         if (updateTable) {
//             res.redirect('/tables');
//         }
//     }
// }