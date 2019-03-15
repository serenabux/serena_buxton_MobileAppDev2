//
//  TableViewController.swift
//  Lab5
//
//  Created by Serena Buxton on 3/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class TableViewController: UITableViewController {
    
    var birthdays = [Birthday]()
    var birthdayData = BirthdayDataController()
    
    func render(){
        birthdays=birthdayData.getBirthdays()
        //reload the table data
        tableView.reloadData()
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        //assign the closure with the method we want called to the onDataUpdate closure
        birthdayData.onDataUpdate = {[weak self] (data:[Birthday]) in
            self?.render()}
        birthdayData.setupFirebaseListener()
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return birthdays.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "birthdayRow", for: indexPath)

        let birthday = birthdays[indexPath.row]
        cell.textLabel!.text = birthday.name

        return cell
    }
    

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
 

    
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            let birthdayID = birthdays[indexPath.row].id
            birthdayData.deleteBirthday(birthdayID: birthdayID)
            
        }
    }


    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
        if segue.identifier == "birthdaySegue" {
            let detailVC = segue.destination as! BirthdayViewController
            let indexPath = tableView.indexPath(for: sender as!
                UITableViewCell)!
            let birthday = birthdays[indexPath.row]
            //sets the data for the destination controller
            detailVC.bdayPerson = birthday.name
            detailVC.bday = birthday.date
        }
    }
    
    @IBAction func unwindSegue(segue: UIStoryboardSegue){
        if segue.identifier == "savesegue" {
            let source = segue.source as! AddViewController
            if source.addedname.isEmpty == false {
                birthdayData.addBirthday(name: source.addedname, date: source.addedbirthday)
            }
        }
    }
 

}
