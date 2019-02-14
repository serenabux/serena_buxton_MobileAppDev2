//
//  DetailTableViewController.swift
//  Lab 2
//
//  Created by Serena Buxton on 2/13/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class DetailTableViewController: UITableViewController {
    
    var typeData = workoutDataModelController()
    var selectedType = 0
    var workoutList = [String]()
    var searchController = UISearchController()
    

    override func viewDidLoad() {
        super.viewDidLoad()

        
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        //self.navigationItem.rightBarButtonItem = self.editButtonItem
    }
    
    override func viewWillAppear(_ animated: Bool) {
        workoutList = typeData.getWorkouts(index: selectedType)
        let resultsController = SearchResultsTableViewController()
        resultsController.allwords = workoutList
        searchController = UISearchController(searchResultsController: resultsController)
        searchController.searchBar.placeholder = "Enter a search term"
        tableView.tableHeaderView = searchController.searchBar
        searchController.searchResultsUpdater = resultsController
    }

    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return workoutList.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "workoutIdentifier", for: indexPath)

        cell.textLabel?.text = workoutList[indexPath.row]

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
            //delete the workout from the array
            workoutList.remove(at: indexPath.row)
            //delete the data model instance
            typeData.deleteWorkout(index: selectedType, workoutIndex: indexPath.row)
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }



    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {
        let fromRow = fromIndexPath.row
        let toRow = to.row
        //move in data model instance
        workoutList.swapAt(fromRow, toRow)

    }

    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }

    @IBAction func unwindSegue(_ segue:UIStoryboardSegue){
        if segue.identifier == "donesegue"{
            let source = segue.source as! AddWorkoutViewController
            if((source.addedWorkout.isEmpty)==false){
                typeData.addWorkout(index: selectedType, newWorkout: source.addedWorkout, newIndex: workoutList.count)
                workoutList.append(source.addedWorkout)
                tableView.reloadData()
            }
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
