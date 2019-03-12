//
//  SearchResultsTableViewController.swift
//  Lab 2
//
//  Created by Serena Buxton on 2/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class SearchResultsTableViewController: UITableViewController, UISearchResultsUpdating {
    
    var allwords = [String]()
    var filterWords = [String]()
    

    override func viewDidLoad() {
        super.viewDidLoad()
        tableView.register(UITableViewCell.self, forCellReuseIdentifier: "workoutIdentifier")
        navigationController?.navigationBar.prefersLargeTitles = false
        // Uncomment the following line to preserve selection between presentations
        // self.clearsSelectionOnViewWillAppear = false

        // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
        // self.navigationItem.rightBarButtonItem = self.editButtonItem
    }

    func updateSearchResults(for searchController: UISearchController) {
        navigationController?.navigationBar.prefersLargeTitles = false
        let searchString = searchController.searchBar.text //search string
        filterWords.removeAll(keepingCapacity: true)//removes all elements
        if searchString?.isEmpty == false {
            //closure that will be called for each word to see if it matches the search string
            let searchFilter:(String) -> Bool = {name in
                //look for search as a substring of the word
                let range = name.range(of: searchString!, options: .caseInsensitive)
                return range != nil
            }
            let matches = allwords.filter(searchFilter)
            filterWords.append(contentsOf: matches)
        }
        tableView.reloadData()
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        // #warning Incomplete implementation, return the number of sections
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows
        return filterWords.count
    }


    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "workoutIdentifier", for: indexPath)

        cell.textLabel?.text = filterWords[indexPath.row]

        return cell
    }


    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

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

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
