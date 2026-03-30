import { Component, OnInit } from '@angular/core';
import { PrescriptionService, Prescription } from '../prescription.service';

@Component({
  selector: 'app-prescription-list',
  templateUrl: './prescription-list.component.html',
  styleUrls: ['./prescription-list.component.css']
})
export class PrescriptionListComponent implements OnInit {

  prescriptions: Prescription[] = [];
  searchText: string = '';

  constructor(private prescriptionService: PrescriptionService) { }

  ngOnInit(): void {
    this.getAllPrescriptions();
  }

  getAllPrescriptions() {
    this.prescriptionService.getAll().subscribe(data => {
      this.prescriptions = data;
    });
  }

  deletePrescription(id: number) {
    if(confirm('Are you sure you want to delete this prescription?')) {
      this.prescriptionService.delete(id).subscribe(() => {
        this.getAllPrescriptions();
      });
    }
  }
}
