import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PrescriptionService, Prescription } from '../prescription.service';
import { PatientService } from '../patient.service';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-create-prescription',
  templateUrl: './create-prescription.component.html'
})
export class CreatePrescriptionComponent implements OnInit {

  prescription: Prescription = {
    patient: {},
    doctor: {},
    diagnosis: '',
    medicines: '',
    dosage: '',
    frequency: '',
    duration: '',
    notes: ''
  };

  patientId: number = 0;

  constructor(
    private prescriptionService: PrescriptionService,
    private patientService: PatientService,
    private authService: AuthenticationService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.patientId = this.route.snapshot.params['id'];
    this.patientService.getPatientById(this.patientId).subscribe(data => {
      this.prescription.patient = data;
    });
    
    // In a real app, doctor info would come from the logged-in user context
    // For now, using a placeholder or current user if available
    this.prescription.doctor = { id: 1, name: 'Dr. Admin' }; 
  }

  savePrescription() {
    this.prescriptionService.add(this.prescription).subscribe(data => {
      console.log(data);
      this.router.navigate(['/prescriptions']);
    }, error => console.log(error));
  }

  onSubmit() {
    this.savePrescription();
  }
}
