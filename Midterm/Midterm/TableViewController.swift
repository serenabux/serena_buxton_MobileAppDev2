//
//  TableViewController.swift
//  Midterm
//
//  Created by Serena Buxton on 3/14/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class TableViewController: UITableViewController {
    
    var movies = [String]()
    var movieData = MovieDataModelController()

    override func viewDidLoad() {
        super.viewDidLoad()
        movieData.loadData()
        movies=movieData.getMovies()

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
        return movies.count
    }

    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "movieCell", for: indexPath)

        // Configure the cell...
        cell.textLabel?.text = movies[indexPath.row]
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
            //Delete the country from the array
            movies.remove(at: indexPath.row)
            //Delete from the data model instance
           movieData.deleteMovie(index: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
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
        if segue.identifier == "movieInfoSegue"{
            let detailVC = segue.destination as! MovieViewController
            let indexPath = tableView.indexPath(for: sender as!
                UITableViewCell)!
            let m = movies[indexPath.row]
            let url = movieData.getURL(index: indexPath.row)
            //sets the data for the destination controller
            detailVC.title = m
            detailVC.webpage = url
        }
    }
    
    @IBAction func unwindSegue (_ segue:UIStoryboardSegue){
        if segue.identifier=="saveSegue"{
            let source = segue.source as! AddViewController
            if source.addedMovie.isEmpty == false && source.addedURL.isEmpty == false{
                //add country to our data model instance
            
                movieData.addMovie(newName: source.addedMovie, newURL: source.addedURL)
                //continentsData.addCountry(index: selectedContinent, newCountry: source.addedCountry, newIndex: countryList.count)
                //add country to the array
                movies.append(source.addedMovie)
                tableView.reloadData()
            }
        }
    }
}
