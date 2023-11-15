import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Subscription } from '../Models/Subscription ';
@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private baseUrl = 'http://localhost:8089/api/subscription'; // Update with your API base URL

  constructor(private http: HttpClient) {}

  addSubscription(subscription: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.baseUrl}/add`, subscription);
  }

  getById(id: number): Observable<Subscription> {
    return this.http.get<Subscription>(`${this.baseUrl}/get/${id}`);
  }

  getSubscriptionsByType(typeSub: string): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.baseUrl}/all/${typeSub}`);
  }

  updateSubscription(subscription: Subscription): Observable<Subscription> {
    return this.http.put<Subscription>(`${this.baseUrl}/update`, subscription);
  }

  getSubscriptionsByDates(date1: string, date2: string): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.baseUrl}/all/${date1}/${date2}`);
  }
}
