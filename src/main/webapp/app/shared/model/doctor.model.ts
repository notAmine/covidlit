import { IUser } from 'app/core/user/user.model';
import { IHospital } from 'app/shared/model/hospital.model';

export interface IDoctor {
  id?: number;
  pin?: string;
  user?: IUser;
  hospital?: IHospital;
}

export class Doctor implements IDoctor {
  constructor(public id?: number, public pin?: string, public user?: IUser, public hospital?: IHospital) {}
}
