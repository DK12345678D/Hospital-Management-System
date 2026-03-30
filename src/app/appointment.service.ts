import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from './appointment';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private baseUrl = `${environment.apiUrl}/api/v1/appointments`;


  constructor(private httpClient: HttpClient) { }

  getAppointmentslist(): Observable<Appointment[]> {
    return this.httpClient.get<Appointment[]>(`${this.baseUrl}`);
  }

  createAppointment(appointment: Appointment): Observable<Appointment> {
    return this.httpClient.post<Appointment>(`${this.baseUrl}/book`, appointment);
  }

  getAppointmentById(id: number): Observable<Appointment> {   
    return this.httpClient.get<Appointment>(`${this.baseUrl}/${id}`);
  }


  deleteAppointment(id: number): Observable<Object> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }

}
