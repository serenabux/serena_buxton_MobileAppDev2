//
//  ViewController.swift
//  Lab 2
//
//  Created by Serena Buxton on 2/11/19.
//  Copyright © 2019 Serena Buxton. All rights reserved.
//

import UIKit

class ViewController: UITableViewController {
    
    var typeList = [String]()
    var typeData = workoutDataModelController()

    override func viewDidLoad() {
        super.viewDidLoad()
        typeData.loadData()
        typeList = typeData.getType()
        navigationController?.navigationBar.prefersLargeTitles = true
        
        //application instance
        let app = UIApplication.shared
        //subscribe to the UIApplicationWillResignActiveNotification notification

        NotificationCenter.default.addObserver(self, selector: #selector(ViewController.applicationWillResignActive(_:)), name: UIApplication.willResignActiveNotification, object: app)
        // Do any additional setup after loading the view, typically from a nib.
    }
    
    //called when the UIApplicationWillResignActiveNotification notification is posted
    //all notification methods take a single NSNotification instance of their argument
    @objc func applicationWillResignActive(_ notification: NSNotification){
        typeData.writeData()
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return typeList.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "workoutIdentifier ", for: indexPath)
        cell.textLabel?.text = typeList[indexPath.row]
        return cell
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "workoutSegue" {
            let detailVC = segue.destination as! DetailTableViewController
            let indexPath = tableView.indexPath(for: sender as! UITableViewCell)!
            //set data for the destination
            detailVC.title = typeList[indexPath.row]
            detailVC.typeData = typeData
            detailVC.selectedType = indexPath.row
        }
    }

}

