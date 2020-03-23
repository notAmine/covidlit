import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'hospital',
        loadChildren: () => import('./hospital/hospital.module').then(m => m.CovidlitHospitalModule)
      },
      {
        path: 'bed',
        loadChildren: () => import('./bed/bed.module').then(m => m.CovidlitBedModule)
      },
      {
        path: 'doctor',
        loadChildren: () => import('./doctor/doctor.module').then(m => m.CovidlitDoctorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class CovidlitEntityModule {}
