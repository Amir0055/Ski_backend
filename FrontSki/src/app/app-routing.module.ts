import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SubscriptionComponent } from './subscription/subscription.component';

const routes: Routes = [  
  { path: 'add-subscription', component: SubscriptionComponent },
// Add other routes as needed
 // { path: '', redirectTo: '/add-subscription', pathMatch: 'full' },  Redirect to add-subscription by default];
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
