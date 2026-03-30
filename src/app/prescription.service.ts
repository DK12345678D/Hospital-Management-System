import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export interface Prescription {
  id?: number;
  patient: any;
  doctor: any;
  diagnosis: string;
  medicines: string;
  dosage: string;
  frequency: string;
  duration: string;
  prescribedAt?: Date;
  notes: string;
}

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  private baseUrl = `${environment.apiUrl}/api/v1/prescriptions`;

  constructor(private httpClient: HttpClient) { }

  getAll(): Observable<Prescription[]> {
    return this.httpClient.get<Prescription[]>(`${this.baseUrl}`);
  }

  getByPatient(id: number): Observable<Prescription[]> {
    return this.httpClient.get<Prescription[]>(`${this.baseUrl}/patient/${id}`);
  }

  getByDoctor(id: number): Observable<Prescription[]> {
    return this.httpClient.get<Prescription[]>(`${this.baseUrl}/doctor/${id}`);
  }

  add(prescription: Prescription): Observable<Prescription> {
    return this.httpClient.post<Prescription>(`${this.baseUrl}/add`, prescription);
  }

  delete(id: number): Observable<any> {
    return this.httpClient.delete(`${this.baseUrl}/${id}`);
  }
}
