import IFloatingWindowParams from './IFloatingWindowParams';

interface IFloatingWindowProvider {
    getWindowParams: () => IFloatingWindowParams;
    handleOpenWindow: () => boolean;
    saveWindowParams: () => void;
    setWindowWith: (width: number) => void;
    setWindowHeight: (height: number, isOpen: boolean = false) => void;
}

export default IFloatingWindowProvider;