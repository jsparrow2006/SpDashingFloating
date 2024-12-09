import React  from 'react';

interface INativeContainers {
    children: React.ReactElement;
}

const NativeContainers: React.FC<INativeContainers> = (props) => {
    const { children } = props

    return (children)
}

export default React.memo(NativeContainers)