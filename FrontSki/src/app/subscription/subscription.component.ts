import { Component, OnInit } from '@angular/core';
import { Subscription } from '../Models/Subscription ';
import { SubscriptionService } from '../Services/subscription.service';

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent  {
  newSubscription: Subscription = {
    numSub: 0,
    startDate: '',
    endDate: '',
    price: 0,
    typeSub: ''
  };

  addedSubscription: Subscription | undefined;
  retrievedSubscription: Subscription | undefined;
  subscriptionsByDates: Subscription[] = [];
  subscriptionsByType: Subscription[] = [];
  constructor(private subscriptionService: SubscriptionService) {}


  onSubmit(): void {
    console.log('Form submitted', this.newSubscription);
    this.subscriptionService.addSubscription(this.newSubscription).subscribe(
      (subscription) => {
        this.addedSubscription = subscription;
        this.newSubscription = { numSub: 0, startDate: '', endDate: '', price: 0, typeSub: '' }; // Clear the form
      },
      (error) => {
        console.error('Error adding subscription:', error);
      }
    );
  }

  retrieveSubscriptionById(id: number): void {
    this.subscriptionService.getById(id).subscribe(
      (subscription) => {
        alert("Start Date:"+ subscription.startDate+

          "End Date:"+ subscription.endDate+
          
          "Price:" +subscription.price+
          
          "Type:" +subscription.typeSub);
        this.retrievedSubscription = subscription;
      },
      (error) => {
        console.error(`Error retrieving subscription with ID ${id}:`, error);
      }
    );
  }
  SetElementForUpdate(subscribtion: Subscription): void {
this.newSubscription=subscribtion;
  }
  getSubscriptionsByDates(startDate: string, endDate: string): void {
    this.subscriptionService.getSubscriptionsByDates(startDate, endDate).subscribe(
      (subscriptions) => {
        console.log(subscriptions);
        this.subscriptionsByDates = subscriptions;
      },
      (error) => {
        console.error(`Error retrieving subscriptions by dates:`, error);
      }
    );
  }
  getSubscriptionsByType(typeSub: string): void {
    this.subscriptionService.getSubscriptionsByType(typeSub).subscribe(
      (subscriptions) => {
        console.log(subscriptions);
        
        this.subscriptionsByType = subscriptions;
        this.subscriptionsByDates =subscriptions;
      },
      (error) => {
        console.error(`Error retrieving subscriptions by type:`, error);
      }
    );
  }
  onUpdateSubscription(): void {
    this.subscriptionService.updateSubscription(this.newSubscription).subscribe(
      (updatedSubscription) => {
        // Handle the updated subscription as needed
        console.log('Subscription updated:', updatedSubscription);
      },
      (error) => {
        console.error('Error updating subscription:', error);
      }
    );
  }
  // Add other component methods as needed

}